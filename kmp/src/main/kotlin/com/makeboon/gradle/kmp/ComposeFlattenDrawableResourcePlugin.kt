package com.makeboon.gradle.kmp

import com.android.ide.common.vectordrawable.Svg2Vector
import com.makeboon.gradle.extension.asFile
import com.makeboon.gradle.kmp.ComposeFlattenDrawableResourceExtension.Companion.EXTENSION_NAME
import com.makeboon.gradle.kmp.ComposeFlattenDrawableResourceTask.Companion.TASK_NAME
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.register
import java.io.ByteArrayOutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.regex.Pattern
import kotlin.io.path.*

public class ComposeFlattenDrawableResourcePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val extension = extensions.create(
            EXTENSION_NAME,
            ComposeFlattenDrawableResourceExtension::class
        )

        afterEvaluate {
            val inputDir = extension.inputDir
            val inputDirFile = inputDir.asFile

            if (inputDir.isBlank()) return@afterEvaluate

            tasks.register<ComposeFlattenDrawableResourceTask>(TASK_NAME) {
                group = "kmp"
                inputDirectory.set(inputDirFile)
                outputDirectory.set("src/commonMain/composeResources/drawable".asFile)
                clearOutputDir.set(extension.clearOutputDir)
            }
            listOf(
                "preBuild",
                "copyNonXmlValueResourcesForCommonMain"
            ).mapNotNull { runCatching { tasks.named(it) }.getOrNull() }
                .forEach { it.invoke { dependsOn(TASK_NAME) } }
        }
    }
}

public abstract class ComposeFlattenDrawableResourceExtension {
    public companion object {
        public const val EXTENSION_NAME: String = "composeFlattenDrawableResource"
    }

    public var inputDir: String = ""
    public var clearOutputDir: Boolean = true
}

public abstract class ComposeFlattenDrawableResourceTask : DefaultTask() {
    public companion object {
        public const val TASK_NAME: String = "composeFlattenDrawableResource"
        private val INVALID_XML_PATTERN = Pattern.compile("pathData=\"\\s*\"")
    }

    @get:InputDirectory
    public abstract val inputDirectory: DirectoryProperty

    @get:OutputDirectory
    public abstract val outputDirectory: DirectoryProperty

    @get:Input
    public abstract val clearOutputDir: Property<Boolean>

    @TaskAction
    public fun action() {
        val inputDir = inputDirectory.get().asFile
        val outputDir = outputDirectory.get().asFile
        val clearOutputDir = clearOutputDir.get()

        if (!inputDir.exists()) {
            logger.error("Resource input directory not found: $inputDir")
            return
        }
        if (clearOutputDir)
            outputDir.deleteRecursively()
        outputDir.mkdirs()

        val inputDirPath = inputDir.toPath()
        val outputDirPath = outputDir.toPath()
        val resPaths = Files.walk(inputDirPath)
            .filter {
                if (!it.isRegularFile()) return@filter false
                val ext = it.extension
                listOf("svg", "png", "jpg", "jpeg").any { ext.equals(it, ignoreCase = true) }
            }
            .toList()
        if (resPaths.isEmpty()) {
            logger.lifecycle("No resource files found in $inputDir")
            return
        }

        val (svgPaths, imgPaths) = resPaths.partition { it.extension == "svg" }
        logger.lifecycle("svg ${svgPaths.size} files and img ${imgPaths.size} files found in $inputDir")

        doAction("Copying", inputDirPath, outputDirPath, imgPaths) { src, dst ->
            Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING)
        }
        doAction(
            "Converting", inputDirPath, outputDirPath, svgPaths,
            { it.replaceAfterLast(".", "xml") }
        ) { src, dst -> convertSingleSvg(src, dst) }
    }

    private fun doAction(
        actionName: String,
        inputDirPath: Path,
        outputDirPath: Path,
        resPaths: List<Path>,
        transform: (String) -> String = { it },
        block: (src: Path, dst: Path) -> Unit,
    ) {
        val totalFileCount = resPaths.size
        val digit = totalFileCount.toString().length
        logger.lifecycle("$actionName $totalFileCount files")

        resPaths.forEachIndexed { index, resPath ->
            val relativePath = inputDirPath.relativize(resPath)
            val fileName = relativePath.pathString
                .replace('\\', '_')
                .replace('/', '_')
                .replace(" ", "--")
                .let(transform)

            logger.lifecycle(buildString {
                append(actionName)
                append(" [%0${digit}d/$totalFileCount]: ")
                append(relativePath)
                append(" => ")
                append(fileName)
            }.format(index + 1))

            block(resPath, Path(outputDirPath.pathString, fileName))
        }
    }

    private fun convertSingleSvg(svgPath: Path, outFile: Path) {
        val outStream = ByteArrayOutputStream()
        val errorLog = Svg2Vector.parseSvgToXml(svgPath, outStream)

        val xmlContent = outStream.toString()
        if (xmlContent.isEmpty()) {
            logger.error("Error converting ${svgPath.name}: $errorLog")
            return
        }
        if (INVALID_XML_PATTERN.matcher(xmlContent).find()) {
            logger.error("Invalid VectorDrawable produced ${svgPath.name}: $errorLog")
            return
        }
        outFile.deleteIfExists()
        outFile.writeText(xmlContent, Charsets.UTF_8)
    }
}
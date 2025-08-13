package pro.crestfi.kmp.gradle

import com.android.ide.common.vectordrawable.Svg2Vector
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.register
import pro.crestfi.gradle.asFile
import pro.crestfi.kmp.gradle.ComposeFlattenDrawableResourceExtension.Companion.EXTENSION_NAME
import pro.crestfi.kmp.gradle.ComposeFlattenDrawableResourceTask.Companion.TASK_NAME
import java.io.ByteArrayOutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.regex.Pattern
import kotlin.io.path.*

class ComposeFlattenDrawableResourcePlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val extension = extensions.create(EXTENSION_NAME, ComposeFlattenDrawableResourceExtension::class)

        afterEvaluate {
            val inputDir = extension.inputDir
            val inputDirFile = inputDir.asFile

            if (inputDir.isBlank())
                throw GradleException("A valid input directory must be specified.")

            tasks.register<ComposeFlattenDrawableResourceTask>(TASK_NAME) {
                group = "kmp"
                inputDirectory.set(inputDirFile)
                outputDirectory.set("src/commonMain/composeResources/drawable".asFile)
                clearOutputDir.set(extension.clearOutputDir)
            }
            listOf(
                "preBuild",
                "copyNonXmlValueResourcesForCommonMain"
            ).forEach { tasks.named(it) { dependsOn(TASK_NAME) } }
        }
    }
}

abstract class ComposeFlattenDrawableResourceExtension {
    companion object {
        const val EXTENSION_NAME = "composeFlattenDrawableResource"
    }

    var inputDir: String = ""
    var clearOutputDir: Boolean = true
}

abstract class ComposeFlattenDrawableResourceTask : DefaultTask() {
    companion object {
        const val TASK_NAME = "composeFlattenDrawableResource"
        private val INVALID_XML_PATTERN = Pattern.compile("pathData=\"\\s*\"")
    }

    @get:InputDirectory
    abstract val inputDirectory: DirectoryProperty

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @get:Input
    abstract val clearOutputDir: Property<Boolean>

    @TaskAction
    fun action() {
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
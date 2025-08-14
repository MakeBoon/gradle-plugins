package pro.crestfi.kmp.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeCompilation
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import java.io.File
import java.io.InputStream
import java.net.URL
import kotlin.io.path.createTempFile

class iOSLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        extensions.configure<KotlinMultiplatformExtension> {
            mutableListOf<KotlinNativeTarget>().apply {
                add(iosArm64())
                add(iosSimulatorArm64())
            }.forEach(KotlinNativeTarget::configureCinterop)
        }
    }
}

private fun resource(vararg paths: String): URL? =
    iOSLibraryPlugin::class.java.getResource("/${paths.joinToString("/")}")

private fun resourceStream(vararg paths: String): InputStream? =
    iOSLibraryPlugin::class.java.getResourceAsStream("/${paths.joinToString("/")}")

private fun fileFromResource(vararg paths: String): File? =
    resourceStream(*paths)?.let { input ->
        createTempFile()
            .toFile()
            .apply {
                deleteOnExit()
                outputStream().use { input.copyTo(it) }
            }
    }

const val DIR_cinterop = "nativeInterop/cinterop"

/**
 * https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-dsl-reference.html#cinterops
 * The default file path is src/nativeInterop/cinterop/<interop-name>.def
 */
private fun KotlinNativeTarget.configureCinterop() {
    compilations.getByName("main") {
        // execute in 'kmp-shared' module
//        create("NSKeyValueObserving")
    }
}

private fun KotlinNativeCompilation.create(name: String) {
    cinterops.create(name) {
        definitionFile.set(fileFromResource(DIR_cinterop, "$name.def")!!)
    }
}

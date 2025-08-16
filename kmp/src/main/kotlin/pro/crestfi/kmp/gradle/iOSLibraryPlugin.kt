package pro.crestfi.kmp.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeCompilation
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import pro.crestfi.gradle.fileFromResource

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
        definitionFile.set(
            iOSLibraryPlugin::class
                .fileFromResource(DIR_cinterop, "$name.def")!!
        )
    }
}

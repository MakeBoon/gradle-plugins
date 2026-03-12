package com.makeboon.kmp.gradle

import com.makeboon.gradle.fileFromResource
import com.makeboon.gradle.with
import com.makeboon.kmp.iosTargets
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeCompilation
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

@Suppress("ClassName")
class iOSTargetPlugin(
    private val library: Boolean,
) : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        extensions.configure<KotlinMultiplatformExtension> {
            val targets = iosTargets
            targets.forEach(KotlinNativeTarget::configureCinterop)
        }
    }
}

const val DIR_cinterop = "src/nativeInterop/cinterop"

/**
 * https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-dsl-reference.html#cinterops
 * The default file path is src/nativeInterop/cinterop/<interop-name>.def
 *
 * compilations.getByName("main") {
 *     val nsKeyValueObserving by cinterops.creating
 * }
 */
private fun KotlinNativeTarget.configureCinterop() {
    compilations.getByName("main") {
        project.projectDir
            .with(DIR_cinterop)
            .listFiles()?.forEach {
                cinterops.create(it.nameWithoutExtension) {
                    definitionFile.set(it)
                }
            }
    }
}

private fun KotlinNativeCompilation.create(name: String) {
    cinterops.create(name) {
        definitionFile.set(
            iOSTargetPlugin::class
                .fileFromResource(DIR_cinterop, "$name.def")!!
        )
    }
}

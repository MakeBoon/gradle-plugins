package com.makeboon.gradle.kmp

import com.makeboon.gradle.extension.filesInProjectDir
import com.makeboon.gradle.kmp.extension.iosTargets
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

@Suppress("ClassName")
public object iOSTargetPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        extensions.configure<KotlinMultiplatformExtension> {
            iosTargets.forEach(KotlinNativeTarget::configureCinterop)
        }
    }
}

public const val DIR_cinterop: String = "src/nativeInterop/cinterop"

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
        project.filesInProjectDir(DIR_cinterop)
            .forEach {
                cinterops.create(it.nameWithoutExtension) {
                    definitionFile.set(it)
                }
            }
    }
}

package com.makeboon.gradle.kmp.extension

import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

public val KotlinMultiplatformExtension.iosTargets: List<KotlinNativeTarget>
    get() = listOf(iosArm64(), iosSimulatorArm64())
public val KotlinMultiplatformExtension.iosTargetsWithLegacy: List<KotlinNativeTarget>
    get() = listOf(iosArm64(), iosSimulatorArm64(), iosX64())

public fun KotlinMultiplatformExtension.xcFramework(
    name: String = "ComposeApp",
    targets: List<KotlinNativeTarget> = iosTargets,
    configure: Framework.() -> Unit,
) {
    val (xcf, appConfig) = with(project) {
        XCFramework(name) to extensions.getByType<AppConfig>()
    }
    targets.forEach {
        it.binaries.framework {
            baseName = name
            isStatic = true

            with(appConfig) {
                binaryOption("bundleId", "$projectNamespace.$name")
                binaryOption("bundleShortVersionString", versionName)
                binaryOption("bundleVersion", "$versionCode")
            }

            configure.invoke(this)

            xcf.add(this)
        }
    }
}

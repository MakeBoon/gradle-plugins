package com.makeboon.gradle.kmp.extension

import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

/**
 * https://kotlinlang.org/docs/multiplatform/multiplatform-dsl-reference.html#targets
 * https://kotlinlang.org/docs/native-target-support.html
 */
public val KotlinMultiplatformExtension.appleTargets: Array<KotlinNativeTarget>
    get() = iosTargets + macosTargets + watchosTargets + tvosTargets
public val KotlinMultiplatformExtension.iosTargets: Array<KotlinNativeTarget>
    get() = arrayOf(iosArm64(), iosSimulatorArm64())
public val KotlinMultiplatformExtension.macosTargets: Array<KotlinNativeTarget>
    get() = arrayOf(macosArm64())
public val KotlinMultiplatformExtension.watchosTargets: Array<KotlinNativeTarget>
    get() = arrayOf(watchosDeviceArm64(), watchosArm64(), watchosSimulatorArm64())
public val KotlinMultiplatformExtension.tvosTargets: Array<KotlinNativeTarget>
    get() = arrayOf(tvosArm64(), tvosSimulatorArm64())

public val KotlinMultiplatformExtension.iosMacosTargets: Array<KotlinNativeTarget>
    get() = iosTargets + macosTargets

public fun KotlinMultiplatformExtension.xcFramework(
    name: String = "ComposeApp",
    targets: Array<KotlinNativeTarget> = iosTargets,
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

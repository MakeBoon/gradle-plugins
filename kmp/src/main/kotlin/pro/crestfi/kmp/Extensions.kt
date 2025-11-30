package pro.crestfi.kmp

import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

val Project.appConfig: AppConfigExtension
    get() = extensions.getByType<AppConfigExtension>()
val KotlinMultiplatformExtension.iosTargets: List<KotlinNativeTarget>
    get() = listOf(iosArm64(), iosSimulatorArm64())
val KotlinMultiplatformExtension.iosTargetsWithLegacy: List<KotlinNativeTarget>
    get() = listOf(iosArm64(), iosSimulatorArm64(), iosX64())

fun KotlinMultiplatformExtension.xcFramework(
    name: String = "ComposeApp",
    targets: List<KotlinNativeTarget> = iosTargets,
    configure: Framework.() -> Unit,
) {
    val (xcf, appConfig) = with(project) {
        XCFramework(name) to appConfig
    }
    targets.forEach {
        it.binaries.framework {
            baseName = name
            isStatic = true

            binaryOption("bundleId", "${appConfig.projectNamespace}.$name")
            binaryOption("bundleShortVersionString", appConfig.versionName)
            binaryOption("bundleVersion", "${appConfig.versionCode}")

            configure.invoke(this)

            xcf.add(this)
        }
    }
}

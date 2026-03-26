package gradle

import com.makeboon.gradle.create
import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings

class DependencyResolutionManagementPlugin : Plugin<Settings> {
    override fun apply(target: Settings) = target.dependencyResolutionManagement(buildSrc = false)
}

class DependencyResolutionManagementForBuildSrcPlugin : Plugin<Settings> {
    override fun apply(target: Settings) = target.dependencyResolutionManagement(buildSrc = true)
}

private fun Settings.dependencyResolutionManagement(
    resources: Boolean = true,
    buildSrc: Boolean = false,
) {
    dependencyResolutionManagement {
        repositories {
            google {
                mavenContent {
                    includeGroupAndSubgroups("androidx")
                    includeGroupAndSubgroups("com.android")
                    includeGroupAndSubgroups("com.google")
                }
            }
            mavenCentral()
        }
        create(
            versionCatalogs, resources, buildSrc,
            "core" to "build-plugins",
            "makeboon" to "makeboon-plugins",
            "kmp" to "kmp",
            "kmpExt" to "kmp-ext",
            "kmpAndroid" to "kmp-android",
            "kmpIos" to "kmp-ios",
            "kmpApp" to "kmp-app",
        )
    }
}

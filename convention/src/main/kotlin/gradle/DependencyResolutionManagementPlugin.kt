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
    resource: Boolean = true,
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
        versionCatalogs {
            create(this, "core", "build-plugins", resource, buildSrc)
            create(this, "crestfi", "crestfi-plugins", resource, buildSrc)
            create(this, "kmp", "kmp", resource, buildSrc)
            create(this, "kmpExt", "kmp-ext", resource, buildSrc)
            create(this, "kmpAndroid", "kmp-android", resource, buildSrc)
            create(this, "kmpIos", "kmp-ios", resource, buildSrc)
            create(this, "kmpApp", "kmp-app", resource, buildSrc)
        }
    }
}

package gradle

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import pro.crestfi.gradle.create

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
            create(this, "kmpShared", "kmp-shared", resource, buildSrc)
            create(this, "kmpApp", "kmp-app", resource, buildSrc)
        }
    }
}

package gradle

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import pro.crestfi.gradle.create

class DependencyResolutionManagementPlugin : Plugin<Settings> {
    override fun apply(target: Settings) = with(target) {
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
                create(this, "core", "build-plugins")
                create(this, "crestfi", "crestfi-plugins")
                create(this, "kmpShared", "kmp-shared")
                create(this, "kmpApp", "kmp-app")
            }
        }
    }
}
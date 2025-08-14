package pro.crestfi.kmp.gradle

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import pro.crestfi.gradle.libs
import pro.crestfi.gradle.pluginId
import pro.crestfi.gradle.versionInt
import pro.crestfi.gradle.versionIntOrNull

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.pluginId("android-library"))
            apply(libs.pluginId("kotlin-parcelize"))
        }

        extensions.configure<KotlinMultiplatformExtension> {
            androidTarget()
        }

        extensions.configure<LibraryExtension> {
            namespace = "$group.$name"
            compileSdk = libs.versionInt("android-compileSdk")
            compileSdkExtension = libs.versionIntOrNull("android-compileSdkExt")

            defaultConfig {
                minSdk = libs.versionInt("android-minSdk")
            }
        }
    }
}
package pro.crestfi.kmp.gradle

import com.android.build.api.dsl.androidLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import pro.crestfi.gradle.core
import pro.crestfi.gradle.kmpAndroid
import pro.crestfi.gradle.pluginId
import pro.crestfi.gradle.versionInt
import pro.crestfi.gradle.versionIntOrNull

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(kmpAndroid.pluginId("library"))
            apply(core.pluginId("kotlin-parcelize"))
        }

        extensions.configure<KotlinMultiplatformExtension> {
            androidLibrary {
                namespace = "$group.$name"
                compileSdk = kmpAndroid.versionInt("compileSdk")
                compileSdkExtension = kmpAndroid.versionIntOrNull("compileSdkExt")
                minSdk = kmpAndroid.versionInt("minSdk")

                androidResources { enable = true }
            }
        }
    }
}
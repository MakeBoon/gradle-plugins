package pro.crestfi.kmp.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import pro.crestfi.gradle.bom
import pro.crestfi.gradle.kmp
import pro.crestfi.gradle.library
import pro.crestfi.gradle.pluginId

class FirebasePlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(kmp.pluginId("gms-googleServices"))
            apply(kmp.pluginId("firebase-appDistribution"))
            apply(kmp.pluginId("firebase-crashlytics"))
        }

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.androidMain.dependencies {
                bom(kmp.library("firebase-bom"))
            }
        }
    }
}
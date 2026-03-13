package com.makeboon.kmp.gradle

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import com.makeboon.gradle.*
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class AndroidTargetPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(kmpAndroid.pluginId("library"))
            apply(core.pluginId("kotlin-parcelize"))
        }

        val moduleNamespace = "$group.$name"
        extensions.configure<KotlinMultiplatformExtension> {
            targets.withType(KotlinMultiplatformAndroidLibraryTarget::class.java).configureEach {
                namespace = moduleNamespace
                compileSdk {
                    version = with(kmpAndroid) {
                        release(versionInt("compileSdk")) {
                            minorApiLevel = versionIntOrNull("compileSdkApi")
                            sdkExtension = versionIntOrNull("compileSdkExt")
                        }
                    }
                }
                minSdk = kmpAndroid.versionInt("minSdk")

                androidResources { enable = true }
            }
        }
    }
}
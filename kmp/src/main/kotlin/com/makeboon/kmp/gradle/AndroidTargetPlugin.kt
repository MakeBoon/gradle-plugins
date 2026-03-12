package com.makeboon.kmp.gradle

import com.android.build.api.dsl.androidLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import com.makeboon.gradle.core
import com.makeboon.gradle.kmpAndroid
import com.makeboon.gradle.pluginId
import com.makeboon.gradle.versionInt
import com.makeboon.gradle.versionIntOrNull

class AndroidTargetPlugin : Plugin<Project> {
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
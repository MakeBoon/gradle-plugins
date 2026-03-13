package com.makeboon.kmp.gradle

import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.impl.VariantOutputImpl
import com.makeboon.gradle.*
import com.makeboon.kmp.AppConfigPlugin
import com.makeboon.kmp.appConfig
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.internal.extensions.stdlib.capitalized
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.register
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        AppConfigPlugin().apply(target)
        androidTarget()
//        OptInPlugin()
        FirebasePlugin().apply(target)
    }

    private fun Project.androidTarget() {
        val appConfig = appConfig

        with(pluginManager) {
            apply(kmpAndroid.pluginId("application"))
            apply(core.pluginId("kotlin-parcelize"))
        }

        extensions.configure<ApplicationExtension> {
            namespace = appConfig.projectNamespace.lowercase()
            compileSdk {
                version = with(kmpAndroid) {
                    release(versionInt("compileSdk")) {
                        minorApiLevel = versionIntOrNull("compileSdkApi")
                        sdkExtension = versionIntOrNull("compileSdkExt")
                    }
                }
            }

            defaultConfig {
                applicationId = namespace
                minSdk = kmpAndroid.versionInt("minSdk")
//                targetSdk { version = release(kmpAndroid.versionInt("compileSdk")) }
                versionCode = appConfig.versionCode
                versionName = appConfig.versionName
            }
            androidResources {
                // https://developer.android.com/guide/topics/resources/app-languages
                generateLocaleConfig = true
            }
            packaging {
                resources {
                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
                }
            }

            val resourcesDir = layout.projectDirectory.file("../../kmp-resources").asFile

            val keystoreDir = resourcesDir.with("android")
            signingConfigs {
                register("release") {
                    with(keystoreDir.with("android.properties").toProperties()) {
                        storeFile = keystoreDir.with(getProperty("StoreFile"))
                        storePassword = getProperty("StorePassword")
                        keyAlias = getProperty("KeyAlias")
                        keyPassword = getProperty("KeyPassword")
                    }
                }
            }

            val proguardDir = resourcesDir.with("proguard")
            buildTypes {
                debug {
                    if (!appConfig.paymentTest) applicationIdSuffix = ".dev"
                    versionNameSuffix = "-dev"
                }
                release {
                    isMinifyEnabled = true
                    isShrinkResources = true
                    signingConfig = signingConfigs["release"]
                    setProguardFiles(
                        listOf(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro",
                            *proguardDir.listFiles()!!
                        )
                    )
                }
            }
        }
        extensions.configure<ApplicationAndroidComponentsExtension> {
            onVariants { variant ->
                with(variant) {
                    val capitalizedName = name.capitalized()
                    val fileName = listOf(
                        rootProject.name,
                        capitalizedName,
                        "v${appConfig.versionCode}",
                        "c${appConfig.versionName}",
                        "${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmm"))}"
                    ).joinToString("-")

                    // APK
                    outputs.mapNotNull { it as? VariantOutputImpl }
                        .forEach { it.outputFileName.set("$fileName.apk") }

                    // BUNDLE
                    val copy = tasks.register<Copy>("copy${capitalizedName}Bundle") {
                        val output = artifacts.get(SingleArtifact.BUNDLE).get().asFile
                        from(output)
                        into(layout.buildDirectory.dir("outputs/bundle/_"))
                        rename { "$fileName.aab" }
                    }
                    tasks.matching { it.name == "bundle${capitalizedName}" }
                        .configureEach { finalizedBy(copy) }
                }
            }
        }
    }
}

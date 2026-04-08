package com.makeboon.gradle.kmp

import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.impl.VariantOutputImpl
import com.makeboon.gradle.extension.*
import com.makeboon.gradle.kmp.extension.AppConfig
import com.makeboon.gradle.kmp.extension.core
import com.makeboon.gradle.kmp.extension.kmpAndroid
import com.makeboon.gradle.kmp.extension.release
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.internal.extensions.stdlib.capitalized
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.register
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

public class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val appConfig = AppConfig.configure(target)

        with(pluginManager) {
            apply(kmpAndroid.plugins.application)
            apply(core.plugins.kotlin.parcelize)
        }

        applicationExtension(appConfig)
        applicationAndroidComponentsExtension(appConfig)
    }

    private fun Project.applicationExtension(appConfig: AppConfig) =
        extensions.configure<ApplicationExtension> {
            namespace = appConfig.projectNamespace.lowercase()
            with(kmpAndroid.versions) {
                compileSdk {
                    version = release(compileSdk, compileSdkApi, compileSdkExt)
                }

                defaultConfig {
                    applicationId = namespace
                    minSdk { version = release(this@with.minSdk) }
//                targetSdk { version = release(compileSdk)) }
                    versionCode = appConfig.versionCode
                    versionName = appConfig.versionName
                }
            }
            androidResources {
                // https://developer.android.com/guide/topics/resources/app-languages
                generateLocaleConfig = true
            }
            packaging { resources {} }

            val keyStoreDir = fileInRootDir(appConfig.keyStoreDir)
            signingConfigs {
                register("release") {
                    with(keyStoreDir.with("android.properties").toProperties()) {
                        storeFile = keyStoreDir.with(getProperty("StoreFile"))
                        storePassword = getProperty("StorePassword")
                        keyAlias = getProperty("KeyAlias")
                        keyPassword = getProperty("KeyPassword")
                    }
                }
            }

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
                            *filesInRootDir(appConfig.proguardDir).toTypedArray(),
                            *filesInProjectDir("proguard").toTypedArray(),
                        )
                    )
                }
            }
        }

    private fun Project.applicationAndroidComponentsExtension(appConfig: AppConfig) =
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
                    outputs.filterIsInstance<VariantOutputImpl>()
                        .forEach { it.outputFileName.set("$fileName.apk") }

                    // BUNDLE
                    val copy = tasks.register<Copy>("copy${capitalizedName}Bundle") {
                        val output = artifacts.get(SingleArtifact.BUNDLE).get().asFile
                        from(output)
                        into(dirInBuildDir("outputs/bundle/_"))
                        rename { "$fileName.aab" }
                    }
                    tasks.matching { it.name == "bundle${capitalizedName}" }
                        .configureEach { finalizedBy(copy) }
                }
            }
        }
}

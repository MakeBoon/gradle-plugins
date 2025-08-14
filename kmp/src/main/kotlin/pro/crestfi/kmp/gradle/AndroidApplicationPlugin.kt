package pro.crestfi.kmp.gradle

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import pro.crestfi.gradle.*

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.pluginId("android-application"))
            apply(libs.pluginId("kotlin-parcelize"))
        }

        extensions.configure<KotlinMultiplatformExtension> {
            androidTarget()
        }

        extensions.configure<ApplicationExtension> {
            compileSdk = libs.versionInt("android-compileSdk")
            compileSdkExtension = libs.versionIntOrNull("android-compileSdkExt")

            defaultConfig {
                minSdk = libs.versionInt("android-minSdk")
                targetSdk = libs.versionInt("android-targetSdk")
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

            val resourcesDir = rootDir.with("resources/kmp")
            signingConfigs {
                register("release") {
                    with(resourcesDir.with("android.properties").toProperties()) {
                        storeFile = resourcesDir.with(getProperty("StoreFile"))
                        storePassword = getProperty("StorePassword")
                        keyAlias = getProperty("KeyAlias")
                        keyPassword = getProperty("KeyPassword")
                    }
                }
            }

            val proguardDir = resourcesDir.with("proguard")
            buildTypes {
                debug {
                    applicationIdSuffix = ".dev"
                    versionNameSuffix = "-dev"
                }
                release {
                    isMinifyEnabled = true
                    isShrinkResources = true
                    signingConfig = signingConfigs["release"]
                    setProguardFiles(
                        listOf(
                            getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro",
                            *proguardDir.listFiles()!!
                        )
                    )
                }
            }
        }
    }
}
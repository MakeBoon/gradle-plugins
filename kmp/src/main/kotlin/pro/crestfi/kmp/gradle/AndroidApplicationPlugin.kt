package pro.crestfi.kmp.gradle

import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.android.build.gradle.internal.tasks.FinalizeBundleTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.extensions.stdlib.capitalized
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.named
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import pro.crestfi.gradle.*
import pro.crestfi.kmp.gradle.AppConfigExtension.Companion.EXTENSION_NAME
import java.io.File

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val appConfig = extensions.create(EXTENSION_NAME, AppConfigExtension::class).apply {
            with(layout.projectDirectory.file("Config.properties").asFile.toProperties()) {
                projectNamespace = getProperty("ProjectNamespace")
                versionMajor = getPropertyInt("VersionMajor")
                versionMinor = getPropertyInt("VersionMinor")
                versionPatch = getPropertyInt("VersionPatch")
                versionBuild = getPropertyInt("VersionBuild")
            }
        }

        with(pluginManager) {
            apply(libs.pluginId("android-application"))
            apply(libs.pluginId("kotlin-parcelize"))
        }

        extensions.configure<KotlinMultiplatformExtension> {
            androidTarget()
        }

        extensions.configure<BaseAppModuleExtension> {
            namespace = appConfig.projectNamespace.lowercase()
            compileSdk = libs.versionInt("android-compileSdk")
            compileSdkExtension = libs.versionIntOrNull("android-compileSdkExt")

            defaultConfig {
                applicationId = namespace
                minSdk = libs.versionInt("android-minSdk")
                targetSdk = libs.versionInt("android-targetSdk")
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

            val resourcesDir = layout.projectDirectory.file("../resources/kmp").asFile

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

            applicationVariants.all {
                val fileName = listOf(
                    rootProject.name,
                    "$name",
                    "v${versionName}",
                    "c${versionCode}",
                ).joinToString("-")

                outputs.all {
                    (this as BaseVariantOutputImpl).outputFileName = "$fileName.apk"
                }
                tasks.named<FinalizeBundleTask>("sign${name.capitalized()}Bundle") {
                    finalBundleFile.apply {
                        set(File(asFile.get().parentFile, "$fileName.aab"))
                    }
                }
            }
        }
    }
}

abstract class AppConfigExtension {
    companion object Companion {
        const val EXTENSION_NAME = "appConfig"
    }

    abstract var projectNamespace: String
    abstract var versionMajor: Int
    abstract var versionMinor: Int
    abstract var versionPatch: Int
    abstract var versionBuild: Int
    val versionCode: Int get() = versionMajor * 1_000_000 + versionMinor * 10_000 + versionPatch * 100 + versionBuild
    val versionName: String get() = "$versionMajor.$versionMinor.$versionPatch"
}
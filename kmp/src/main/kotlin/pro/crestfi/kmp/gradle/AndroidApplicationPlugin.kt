package pro.crestfi.kmp.gradle

import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.android.build.gradle.internal.tasks.FinalizeBundleTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.extensions.stdlib.capitalized
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.named
import pro.crestfi.gradle.core
import pro.crestfi.gradle.kmpAndroid
import pro.crestfi.gradle.pluginId
import pro.crestfi.gradle.toProperties
import pro.crestfi.gradle.versionInt
import pro.crestfi.gradle.versionIntOrNull
import pro.crestfi.gradle.with
import pro.crestfi.kmp.AppConfigExtension
import pro.crestfi.kmp.AppConfigPlugin
import java.io.File

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        AppConfigPlugin().apply(target)
        androidTarget()
//        OptInPlugin()
        FirebasePlugin().apply(target)
    }

    private fun Project.androidTarget() {
        val appConfig = extensions.getByType<AppConfigExtension>()

        with(pluginManager) {
            apply(kmpAndroid.pluginId("application"))
            apply(core.pluginId("kotlin-parcelize"))
        }

        extensions.configure<BaseAppModuleExtension> {
            namespace = appConfig.projectNamespace.lowercase()
            compileSdk = kmpAndroid.versionInt("compileSdk")
            compileSdkExtension = kmpAndroid.versionIntOrNull("compileSdkExt")

            defaultConfig {
                applicationId = namespace
                minSdk = kmpAndroid.versionInt("minSdk")
//                targetSdk = compileSdk
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

package com.makeboon.gradle.kmp

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import com.makeboon.gradle.extension.apply
import com.makeboon.gradle.extension.moduleNamespace
import com.makeboon.gradle.kmp.extension.core
import com.makeboon.gradle.kmp.extension.kmpAndroid
import com.makeboon.gradle.kmp.extension.release
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

public class AndroidTargetPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(kmpAndroid.plugins.library)
            apply(core.plugins.kotlin.parcelize)
        }

        extensions.configure<KotlinMultiplatformExtension> {
            targets.withType<KotlinMultiplatformAndroidLibraryTarget>().configureEach {
                namespace = moduleNamespace.replace("-", "_")
                with(kmpAndroid.versions) {
                    compileSdk {
                        version = release(compileSdk, compileSdkApi, compileSdkExt)
                    }
                    minSdk { version = release(minSdk) }
                }
                androidResources { enable = true } // AndroidManifest

//                withHostTestBuilder {}.configure {}
//                withDeviceTestBuilder {}
            }
        }
    }
}
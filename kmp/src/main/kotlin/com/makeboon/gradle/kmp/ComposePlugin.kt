package com.makeboon.gradle.kmp

import com.makeboon.gradle.extension.apply
import com.makeboon.gradle.extension.buildLogic
import com.makeboon.gradle.extension.dirInBuildDir
import com.makeboon.gradle.extension.hasPlugin
import com.makeboon.gradle.extension.kmp
import com.makeboon.gradle.extension.kmpAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

public object ComposePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(buildLogic.plugins.compose.compiler)
            apply(kmp.plugins.compose)
        }

        /**
         * https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-compiler.html
         * https://developer.android.com/develop/ui/compose/compiler
         */
        extensions.configure<ComposeCompilerGradlePluginExtension> {
            val compilerDir = dirInBuildDir("compose-compiler")
            reportsDestination.set(compilerDir)
            metricsDestination.set(compilerDir)
        }

        extensions.configure<ComposeExtension> {
        }

        extensions.findByType<KotlinMultiplatformExtension>()?.apply {
            sourceSets.commonMain.dependencies {
                implementation(kmp.compose.ui.tooling.preview)
            }
        }

        dependencies {
            when {
                hasPlugin(kmpAndroid.plugins.application) -> "debugImplementation"
                hasPlugin(kmpAndroid.plugins.library) -> "androidRuntimeClasspath"
                else -> return@dependencies
            }(kmp.compose.ui.tooling)
        }
    }
}
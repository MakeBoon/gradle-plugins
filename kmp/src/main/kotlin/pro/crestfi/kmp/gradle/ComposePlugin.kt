package pro.crestfi.kmp.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import pro.crestfi.gradle.core
import pro.crestfi.gradle.kmp
import pro.crestfi.gradle.pluginId
import pro.crestfi.gradle.regularFiles

class ComposePlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(core.pluginId("compose-compiler"))
            apply(kmp.pluginId("compose"))
        }

        val resourcesDir = layout.projectDirectory.dir("../resources/kmp")
        val stabilityConfDir = resourcesDir.dir("stability-conf")

        /**
         * https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-compiler.html
         * https://developer.android.com/develop/ui/compose/compiler
         */
        extensions.configure<ComposeCompilerGradlePluginExtension> {
            val compilerDir = layout.buildDirectory.dir("compose-compiler")
            reportsDestination.set(compilerDir)
            metricsDestination.set(compilerDir)
            stabilityConfigurationFiles.addAll(stabilityConfDir.regularFiles)
        }

        extensions.configure<ComposeExtension> {
        }
    }
}
package pro.crestfi.kmp.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import pro.crestfi.gradle.libs
import pro.crestfi.gradle.pluginId

class ComposePlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.pluginId("compose"))
            apply(libs.pluginId("compose-compiler"))
        }

        /**
         * https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-compiler.html
         * https://developer.android.com/develop/ui/compose/compiler
         */
        extensions.configure<ComposeCompilerGradlePluginExtension> {
            with(layout) {
                val compilerDir = buildDirectory.dir("compose-compiler")
                reportsDestination.set(compilerDir)
                metricsDestination.set(compilerDir)
                stabilityConfigurationFiles.addAll(
                    with(projectDirectory) {
                        dir("stability-conf").asFileTree.map { file(it.path) }
                    }
                )
            }
        }

        extensions.configure<ComposeExtension> {
        }
    }
}
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

        // https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-compiler.html
        extensions.configure<ComposeCompilerGradlePluginExtension> {
        }

        extensions.configure<ComposeExtension> {
        }
    }
}
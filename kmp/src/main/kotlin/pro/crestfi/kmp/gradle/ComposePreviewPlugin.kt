package pro.crestfi.kmp.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import pro.crestfi.gradle.libs
import pro.crestfi.gradle.pluginId

class ComposePreviewPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.pluginId("compose"))
        }

        afterEvaluate {
            extensions.configure<KotlinMultiplatformExtension> {
                val compose = extensions.getByType<ComposePlugin.Dependencies>()
                dependencies {
                    "debugImplementation"(compose.uiTooling)
                }
                with(sourceSets) {
                    commonMain.dependencies {
                        api(compose.components.uiToolingPreview)
                    }
                    androidMain.dependencies {
                        api(compose.preview)
                    }
                }
            }
        }
    }
}
package pro.crestfi.kmp.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import pro.crestfi.gradle.bundle
import pro.crestfi.gradle.kmp
import pro.crestfi.gradle.pluginId

class ComposePreviewPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(kmp.pluginId("compose"))
        }

        afterEvaluate {
            extensions.configure<KotlinMultiplatformExtension> {
                with(this@afterEvaluate) {
                    dependencies {
                        // https://developer.android.com/kotlin/multiplatform/plugin#compose-preview-dependencies
//                        "androidRuntimeClasspath"(compose.uiTooling)
                    }
                }
                with(sourceSets) {
                    commonMain.dependencies {
                        api(kmp.bundle("compose-preview"))
                    }
                    androidMain.dependencies {
                        api(kmp.bundle("compose-android"))
                    }
                }
            }
        }
    }
}
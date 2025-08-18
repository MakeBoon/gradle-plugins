package pro.crestfi.kmp.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import pro.crestfi.gradle.`-X`
import pro.crestfi.gradle.libs
import pro.crestfi.gradle.pluginId
import pro.crestfi.gradle.versionInt

class FrameworkPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.pluginId("kotlin-multiplatform"))
            apply(libs.pluginId("kotlin-serialization"))
        }

        extensions.configure<KotlinMultiplatformExtension> {
            jvmToolchain(libs.versionInt("kotlin-jvmToolchain"))
            compilerOptions {
                val kotlinVersion = KotlinVersion.KOTLIN_2_2
                languageVersion.set(kotlinVersion)
                apiVersion.set(kotlinVersion)
                freeCompilerArgs.addAll(
                    `-X`(
                        "expect-actual-classes",
                        "context-parameters",
                        "context-sensitive-resolution"
                    )
                )
            }
        }
    }
}
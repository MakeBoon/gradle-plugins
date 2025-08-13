package pro.crestfi.kmp.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
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
        }
    }
}
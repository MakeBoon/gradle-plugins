package gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import pro.crestfi.gradle.core
import pro.crestfi.gradle.library
import pro.crestfi.gradle.versionInt

class JVMToolchainPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        dependencies {
            "implementation"(core.library("gradlePlugin-kotlin-api"))
        }
        extensions.configure<KotlinProjectExtension> {
            jvmToolchain(core.versionInt("kotlin-jvmToolchain"))
        }
    }
}
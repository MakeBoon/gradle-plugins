package gradle

import com.makeboon.gradle.core
import com.makeboon.gradle.library
import com.makeboon.gradle.versionInt
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

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
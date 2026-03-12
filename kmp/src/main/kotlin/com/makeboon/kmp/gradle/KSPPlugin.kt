package com.makeboon.kmp.gradle

import com.makeboon.gradle.core
import com.makeboon.gradle.pluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KSPPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(core.pluginId("ksp"))
        }

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.commonMain {
                kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
            }
        }
    }
}
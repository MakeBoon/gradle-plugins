package com.makeboon.kmp.gradle

import androidx.room.gradle.RoomExtension
import com.google.devtools.ksp.gradle.KspExtension
import com.makeboon.gradle.*
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.extensions.stdlib.capitalized
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

class RoomPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(core.pluginId("ksp"))
            apply(kmp.pluginId("room"))
        }

        extensions.configure<KspExtension> {
            arg("room.incremental", "true")
        }

        extensions.configure<RoomExtension> {
            generateKotlin = true
            // The schemas directory contains a schema file for each version of the Room database.
            // This is required to enable Room auto migrations.
            // See https://developer.android.com/reference/kotlin/androidx/room/AutoMigration.
            schemaDirectory("$projectDir/schemas")
        }

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.commonMain.dependencies {
                api(kmp.bundle("room"))
            }
        }

        afterEvaluate {
            val compiler = kmp.library("room-compiler")
            extensions.configure<KotlinMultiplatformExtension> {
                with(this@afterEvaluate) {
                    dependencies {
//                        "testImplementation"(kmp.library("room-testing"))
//                        "kspCommonMainMetadata"(compiler)
                        targets.filter { it.platformType != KotlinPlatformType.common }
                            .map { "ksp${it.targetName.capitalized()}" }
                            .forEach { it.invoke(compiler) }
                    }
                }
            }
        }
    }
}

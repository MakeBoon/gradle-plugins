package com.makeboon.kmp.gradle

import androidx.room3.gradle.RoomExtension
import com.makeboon.gradle.bundle
import com.makeboon.gradle.core
import com.makeboon.gradle.kmp
import com.makeboon.gradle.library
import com.makeboon.gradle.pluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class Room3Plugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(core.pluginId("ksp"))
            apply(kmp.pluginId("room3"))
        }

        extensions.configure<RoomExtension> {
            // The schemas directory contains a schema file for each version of the Room database.
            // This is required to enable Room auto migrations.
            // See https://developer.android.com/reference/kotlin/androidx/room3/AutoMigration.
            schemaDirectory("$projectDir/schemas")
        }

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.commonMain.dependencies {
                api(kmp.bundle("room3"))
            }
        }

        dependencies {
            add("ksp", kmp.library("room3-compiler"))
        }
    }
}

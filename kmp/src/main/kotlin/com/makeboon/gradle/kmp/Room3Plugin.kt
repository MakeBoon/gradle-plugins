package com.makeboon.gradle.kmp

import androidx.room3.gradle.RoomExtension
import com.makeboon.gradle.extension.apply
import com.makeboon.gradle.extension.buildLogic
import com.makeboon.gradle.extension.kmp
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

public object Room3Plugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(buildLogic.plugins.ksp)
            apply(kmp.plugins.room3)
        }

        extensions.configure<RoomExtension> {
            // The schemas directory contains a schema file for each version of the Room database.
            // This is required to enable Room auto migrations.
            // See https://developer.android.com/reference/kotlin/androidx/room3/AutoMigration.
            schemaDirectory("$projectDir/schemas")
        }

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.commonMain.dependencies {
                api(kmp.bundles.room3)
            }
        }

        dependencies {
            add("ksp", kmp.room3.compiler)
        }
    }
}

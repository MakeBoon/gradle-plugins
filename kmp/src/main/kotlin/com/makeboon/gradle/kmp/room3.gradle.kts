package com.makeboon.gradle.kmp

import com.makeboon.gradle.extension.kmp

plugins {
    id("com.makeboon.gradle.ksp")
    id("org.jetbrains.kotlin.multiplatform")
    id("androidx.room3")
}

room3 {
    // The schemas directory contains a schema file for each version of the Room database.
    // This is required to enable Room auto migrations.
    // See https://developer.android.com/reference/kotlin/androidx/room3/AutoMigration.
    schemaDirectory("$projectDir/schemas")
}

kotlin {
    sourceSets.commonMain.dependencies {
        api(kmp.bundles.room3)
    }
}

dependencies {
    ksp(kmp.room3.compiler)
}

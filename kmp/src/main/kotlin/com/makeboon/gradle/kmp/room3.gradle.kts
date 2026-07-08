package com.makeboon.gradle.kmp

import com.makeboon.gradle.extension.kmp
import com.makeboon.gradle.kmp.extension.hasAndroid
import com.makeboon.gradle.kmp.extension.hasJVM
import com.makeboon.gradle.kmp.extension.hasNative
import com.makeboon.gradle.kmp.extension.hasWeb
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

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
    sourceSets {
        commonMain.dependencies {
            api(kmp.bundles.room3)
        }

        fun applyDependencies(
            target: NamedDomainObjectProvider<KotlinSourceSet>,
            lib: Provider<MinimalExternalModuleDependency>
        ) = target.dependencies { api(lib) }

        // https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:sqlite/sqlite-bundled/src/
        if (hasWeb) applyDependencies(webMain, kmp.sqlite.web)
        if (hasJVM) applyDependencies(jvmMain, kmp.sqlite.bundled)
        if (hasAndroid) applyDependencies(androidMain, kmp.sqlite.bundled)
        if (hasNative) applyDependencies(nativeMain, kmp.sqlite.bundled)
    }
}

dependencies {
    ksp(kmp.room3.compiler)
}

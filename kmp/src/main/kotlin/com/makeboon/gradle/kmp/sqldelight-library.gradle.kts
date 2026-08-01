package com.makeboon.gradle.kmp

import com.makeboon.gradle.extension.support

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("app.cash.sqldelight")
}

sqldelight {
    linkSqlite = false
    databases {
        val basePath = "src/main/sqldelight"
        register("AppDatabase") {
            dialect(support.sqldelight.dialect.sqlite)

            packageName = path.replace("[^a-z0-9_]".toRegex(), "_")
            generateAsync = true

            srcDirs(basePath)
            schemaOutputDirectory = file("$basePath/databases")
            deriveSchemaFromMigrations = true
            verifyMigrations = true
        }
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(support.bundles.sqldelight.kmp)
        }
    }
}

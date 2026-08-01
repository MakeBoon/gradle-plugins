package com.makeboon.gradle.kmp

import com.makeboon.gradle.extension.support
import com.makeboon.gradle.kmp.extension.hasAndroid
import com.makeboon.gradle.kmp.extension.hasJVM
import com.makeboon.gradle.kmp.extension.hasNative
import com.makeboon.gradle.kmp.extension.hasWeb

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("app.cash.sqldelight")
}

sqldelight {
    linkSqlite = false
    databases {
        val basePath = "src/commonMain/sqldelight"
        register("AppDatabase") {
            dialect(support.sqldelight.dialect.sqlite)

            packageName = path.replace("[^a-z0-9_]".toRegex(), "_")

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
        if (hasJVM) jvmMain.dependencies {
            implementation(support.sqldelight.driver.sqlite)
        }
        if (hasAndroid) androidMain.dependencies {
            implementation(support.sqldelight.driver.android)
        }
        if (hasNative) nativeMain.dependencies {
            implementation(support.sqldelight.driver.native)
        }
        if (hasWeb) webMain.dependencies {
            implementation(support.sqldelight.driver.web)
//            implementation npm("sql.js", "1.6.2")
//            implementation devNpm("copy-webpack-plugin", "9.1.0")
        }
    }
}

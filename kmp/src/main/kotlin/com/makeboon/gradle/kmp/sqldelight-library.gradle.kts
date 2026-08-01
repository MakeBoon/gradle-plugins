package com.makeboon.gradle.kmp

import com.makeboon.gradle.extension.kmp
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

        val bundledPlatformTargets = listOfNotNull(
            "jvmMain".takeIf { hasJVM },
            "androidMain".takeIf { hasAndroid },
            "nativeMain".takeIf { hasNative },
        )
        bundledPlatformTargets.forEach { sourceSetName ->
            named(sourceSetName).dependencies {
                implementation(kmp.sqlite.bundled)
            }
        }
        if (hasWeb) webMain.dependencies {
            implementation(kmp.sqlite.web)
            implementation(support.sqldelight.driver.web)
//            implementation npm("sql.js", "1.6.2")
//            implementation devNpm("copy-webpack-plugin", "9.1.0")
        }
    }
}

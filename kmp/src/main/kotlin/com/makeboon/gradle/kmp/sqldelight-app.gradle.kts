package com.makeboon.gradle.kmp

import com.makeboon.gradle.kmp.extension.AppConfig

plugins {
    id("com.makeboon.gradle.kmp.sqldelight-library")
}

sqldelight {
    databases {
        val appConfig = extensions.getByType<AppConfig>()
        named("AppDatabase") {
            packageName = "${appConfig.projectNamespace}.db"
        }
    }
}

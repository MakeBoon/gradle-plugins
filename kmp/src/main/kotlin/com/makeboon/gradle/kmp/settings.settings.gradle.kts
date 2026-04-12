package com.makeboon.gradle.kmp

import com.makeboon.gradle.extension.create

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google {
            mavenContent {
                // https://github.com/gradle/gradle/issues/35562
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
                includeGroupAndSubgroups("androidx")
            }
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.PREFER_SETTINGS
    repositories {
        mavenCentral()
        google {
            mavenContent {
                // https://github.com/gradle/gradle/issues/35562
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
                includeGroupAndSubgroups("androidx")
            }
        }
    }
    versionCatalogs.create(
        "core" to "build-plugins",
        "makeboon" to "makeboon-plugins",
        "kmp" to "kmp",
        "kmpExt" to "kmp-ext",
        "kmpAndroid" to "kmp-android",
        "kmpIos" to "kmp-ios",
        "kmpApp" to "kmp-app",
    )
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention")
}

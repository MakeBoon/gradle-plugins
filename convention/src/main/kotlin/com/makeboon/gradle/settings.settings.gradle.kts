package com.makeboon.gradle

import com.makeboon.gradle.extension.createProjectVersionCatalogs

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
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
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
}

createProjectVersionCatalogs()

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention")
}

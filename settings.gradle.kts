rootProject.name = "gradle-plugins"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

includeBuild("../gradle-resources")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
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
    versionCatalogs {
        listOf(
            "core" to "build-plugins",
            "kmp" to "kmp",
            "kmpAndroid" to "kmp-android",
            "kmpIos" to "kmp-ios",
        ).forEach { (name, target) ->
            create(name) {
                from(files("../gradle-resources/versions/$target.toml"))
            }
        }
    }
}

plugins {
    id("com.makeboon.gradle-settings")
}

include(":convention")
include(":kmp")

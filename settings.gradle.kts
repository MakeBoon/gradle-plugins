import com.makeboon.gradle.extension.createDefaultVersionCatalogs

rootProject.name = "gradle-plugins"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

includeBuild("../gradle-resources")

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    includeBuild("build-settings-logic")
    includeBuild("build-logic")
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
}

createDefaultVersionCatalogs()

plugins {
    id("com.makeboon.gradle.gradle-settings")
}

include(
    ":docs",
    ":convention",
    ":kmp",
)

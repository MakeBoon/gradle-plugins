rootProject.name = "build-logic"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    includeBuild("../build-settings-logic")
}

plugins {
    id("com.makeboon.gradle.gradle-settings")
}
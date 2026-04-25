import com.makeboon.gradle.extension.createProjectVersionCatalogs

rootProject.name = "build-logic"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    includeBuild("../build-settings-logic")
}

createProjectVersionCatalogs()

plugins {
    id("com.makeboon.gradle.gradle-settings")
}
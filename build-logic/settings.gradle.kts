import com.makeboon.gradle.extension.createDefaultVersionCatalogs

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    includeBuild("../build-settings-logic")
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

createDefaultVersionCatalogs()

plugins {
    id("com.makeboon.gradle.gradle-settings")
}
import com.makeboon.gradle.extension.createBy

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
    createBy(versionCatalogs, "build-logic")
}

plugins {
    id("com.makeboon.gradle.gradle-settings")
}
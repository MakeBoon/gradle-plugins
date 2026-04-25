import com.makeboon.gradle.extension.createProjectVersionCatalogs
import com.makeboon.gradle.extension.DefaultVersionCatalogNames

rootProject.name = "gradle-plugins"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    includeBuild("build-settings-logic")
    includeBuild("build-logic")
}

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
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

createProjectVersionCatalogs()

plugins {
    id("com.makeboon.gradle.gradle-settings")
}

include(
    ":docs",
    ":convention",
    ":kmp",
)

include(
    *DefaultVersionCatalogNames.map { ":catalog:$it" }.toTypedArray(),
)

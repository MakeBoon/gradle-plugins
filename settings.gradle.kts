import com.makeboon.gradle.extension.DefaultVersionCatalogNames
import com.makeboon.gradle.extension.createProjectVersionCatalogs

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

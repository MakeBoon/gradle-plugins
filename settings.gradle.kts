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

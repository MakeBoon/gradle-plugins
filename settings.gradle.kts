rootProject.name = "gradle-plugins"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

includeBuild("../gradle-resources")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
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
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include(":convention")
include(":kmp")

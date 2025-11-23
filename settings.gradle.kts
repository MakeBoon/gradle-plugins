rootProject.name = "gradle-plugins"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

includeBuild("../gradle-resources")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        listOf(
            "libs" to "build-plugins",
            "kmp" to "kmp"
        ).forEach { (name, target) ->
            create(name) {
                from(files("../gradle-resources/versions/$target.toml"))
            }
        }
    }
}

include(":convention")
include(":kmp")

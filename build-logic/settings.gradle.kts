pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.PREFER_SETTINGS
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    versionCatalogs {
        listOf(
            "core" to "build-plugins",
        ).forEach { (name, target) ->
            create(name) {
                from(files("../../gradle-resources/versions/$target.toml"))
            }
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
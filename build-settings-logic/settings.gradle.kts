rootProject.name = "build-settings-logic"

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
    listOf(
        "build-logic", "kotlinx",
        "support", "makeboon",
        "kmp", "kmp-ext",
        "kmp-android", "kmp-apple",
        "kmp-application",
    ).forEach { target ->
        val path = "../catalog/$target/$target.toml"
        layout.rootDirectory.files(path)
            .takeIf { it.singleFile.exists() }
            ?.also { fileCollection ->
                val name = target.lowercase()
                    .replace(Regex("[\\s-_]+([a-z])")) {
                        it.groupValues[1].uppercase()
                    }
                versionCatalogs.create(name) {
                    from(files(fileCollection))
                }
            }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
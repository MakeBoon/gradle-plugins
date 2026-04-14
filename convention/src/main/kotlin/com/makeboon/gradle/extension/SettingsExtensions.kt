package com.makeboon.gradle.extension

import org.gradle.api.initialization.Settings

public val defaultVersionCatalogNames: List<String> = listOf(
    "build-logic", "makeboon",
    "kmp", "kmp-ext",
    "kmp-android", "kmp-ios",
    "kmp-app",
)

public fun String.toCamelCase(): String =
    lowercase()
        .replace(Regex("[\\s-_]+([a-z])")) {
            it.groupValues[1].uppercase()
        }

public fun Settings.createDefaultVersionCatalogs() {
    createVersionCatalogs(*defaultVersionCatalogNames.toTypedArray())
}

public fun Settings.createVersionCatalogs(vararg targets: String) {
    dependencyResolutionManagement {
        targets.forEach { target ->
            buildString {
                val isBuildLogic = with(rootProject.name) {
                    startsWith("build-") && endsWith("-logic")
                }
                if (isBuildLogic) append("../")
                append("../gradle-resources/versions")
                append("/$target.toml")
            }.let { layout.rootDirectory.files(it) }
                .takeIf { it.singleFile.exists() }
                ?.also {
                    versionCatalogs.create(target.toCamelCase()) {
                        from(it)
                    }
                }
        }
    }
}

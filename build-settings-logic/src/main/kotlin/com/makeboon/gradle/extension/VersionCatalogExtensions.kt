package com.makeboon.gradle.extension

import org.gradle.api.initialization.Settings

public fun Settings.createVersionCatalogs(vararg targets: String) {
    dependencyResolutionManagement {
        targets.forEach { target ->
            buildString {
                val isBuildLogic = with(rootProject.name) {
                    startsWith("build-") && endsWith("-logic")
                }
                if (isBuildLogic) append("../")
                append("convention/src/main/resources/com/makeboon/gradle/catalogs/$target.toml")
            }.also { path ->
                versionCatalogs.create(target.toCamelCase()) {
                    from(layout.rootDirectory.files(path))
                }
            }
        }
    }
}

package com.makeboon.gradle.extension

import org.gradle.api.initialization.Settings
import org.gradle.api.initialization.resolve.MutableVersionCatalogContainer

public fun Settings.configureDependencyManagement(local: Boolean = false) {
    dependencyResolutionManagement {
        repositories {
            mavenCentral()
            google {
                mavenContent {
                    includeGroupAndSubgroups("androidx")
                    includeGroupAndSubgroups("com.android")
                    includeGroupAndSubgroups("com.google")
                }
            }
        }
        versionCatalogs.create(
            local,
            "core" to "build-plugins",
            "makeboon" to "makeboon-plugins",
            "kmp" to "kmp",
            "kmpExt" to "kmp-ext",
            "kmpAndroid" to "kmp-android",
            "kmpIos" to "kmp-ios",
            "kmpApp" to "kmp-app",
        )
    }
}

context(settings: Settings)
public fun MutableVersionCatalogContainer.create(
    local: Boolean = false,
    vararg targets: Pair<String, String>,
) {
    targets.forEach { (name, target) -> create(name, target, local) }
}

context(settings: Settings)
public fun MutableVersionCatalogContainer.create(
    name: String,
    target: String = name,
    local: Boolean = false,
) {
    with(settings) {
        create(name) {
            from(layout.rootDirectory.files(buildString {
                when {
                    local -> {
                        if (rootProject.name == "buildSrc") append("../")
                        append("../gradle-resources/versions")
                        append("/$target.toml")
                    }
                    else -> {
                        append("gradle")
                        append("/$target.versions.toml")
                    }
                }
            }))
        }
    }
}

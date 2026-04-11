package com.makeboon.gradle.extension

import org.gradle.api.initialization.Settings
import org.gradle.api.initialization.resolve.MutableVersionCatalogContainer
import org.gradle.plugin.management.internal.PluginManagementSpecInternal

context(settings: Settings)
public fun MutableVersionCatalogContainer.create(
    vararg targets: Pair<String, String>
) {
    targets.forEach { (name, target) -> create(name, target) }
}

context(settings: Settings)
public fun MutableVersionCatalogContainer.create(
    name: String,
    target: String = name,
) {
    with(settings) {
        buildString {
            val isLocal = (pluginManagement as PluginManagementSpecInternal)
                .includedBuilds
                .any { it.rootDir.name == "gradle-plugins" }
            val isBuildSrc = rootProject.name == "buildSrc"
            when {
                isLocal -> {
                    if (isBuildSrc) append("../")
                    append("../gradle-resources/versions")
                    append("/$target.toml")
                }
                else -> {
                    append("gradle")
                    append("/$target.versions.toml")
                }
            }
        }.let { layout.rootDirectory.files(it) }
            .takeIf { it.singleFile.exists() }
            ?.also { create(name) { from(it) } }
    }
}

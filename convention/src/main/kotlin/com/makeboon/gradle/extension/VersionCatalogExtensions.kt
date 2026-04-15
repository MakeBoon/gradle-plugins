package com.makeboon.gradle.extension

import com.makeboon.gradle.GROUP_ID
import com.makeboon.gradle.LOCAL_ROOT_DIR_NAME
import com.makeboon.gradle.VERSION
import org.gradle.api.initialization.Settings
import org.gradle.plugin.management.internal.PluginManagementSpecInternal

public fun Settings.createVersionCatalogs(vararg targets: String) {
    val localDir = (pluginManagement as PluginManagementSpecInternal)
        .includedBuilds
        .map { it.rootDir }
        .firstOrNull { it.nameWithoutExtension == LOCAL_ROOT_DIR_NAME }

    val (isLocal, relativePath) = when (localDir) {
        null -> false to null
        else -> true to rootDir.toPath().relativize(localDir.toPath())
    }

    dependencyResolutionManagement {
        targets.forEach { target ->
            versionCatalogs.create(target.toCamelCase()) {
                from(
                    when {
                        isLocal -> layout.rootDirectory.files("$relativePath/catalog/$target/$target.toml")
                        else -> "$GROUP_ID:catalog-$target:$VERSION"
                    }
                )
            }
        }
    }
}

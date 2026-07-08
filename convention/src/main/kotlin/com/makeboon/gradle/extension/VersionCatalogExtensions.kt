package com.makeboon.gradle.extension

import com.makeboon.gradle.ROOT_DIR_NAME
import com.makeboon.gradle.VERSION
import org.gradle.api.initialization.Settings
import org.gradle.plugin.management.internal.PluginManagementSpecInternal
import java.io.File

public fun Settings.createVersionCatalogs(vararg targets: String) {
    val localDir = (pluginManagement as PluginManagementSpecInternal)
        .includedBuilds
        .map { it.rootDir }
        .firstOrNull { it.nameWithoutExtension == ROOT_DIR_NAME }

    val (isLocal, relativePath) = when (localDir) {
        null -> false to null
        else -> true to rootDir.toPath().relativize(localDir.toPath())
    }

    dependencyResolutionManagement {
        targets.forEach { target ->
            versionCatalogs.create(target.toCamelCase()) {
                from(
                    layout.rootDirectory.files(
                        when {
                            isLocal -> "$relativePath/catalog/$target/$target.toml"
                            else -> extractBundledCatalog(target).absolutePath
                        }
                    )
                )
            }
        }
    }
}

/**
 * The published plugin bundles each catalog toml as a jar resource
 * (see convention/build.gradle.kts); versionCatalogs.from only reads files,
 * so extract the resource under the consumer's .gradle directory.
 */
private fun Settings.extractBundledCatalog(target: String): File {
    val resource = "/com/makeboon/gradle/catalogs/$target.toml"
    val stream = object {}.javaClass.getResourceAsStream(resource)
        ?: error("Bundled version catalog not found on classpath: $resource")
    val file = rootDir.resolve(".gradle/makeboon/catalogs/$VERSION/$target.toml")
    file.parentFile.mkdirs()
    stream.use { input -> file.outputStream().use(input::copyTo) }
    return file
}

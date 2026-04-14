package com.makeboon.gradle.extension

import org.gradle.api.initialization.Settings
import org.gradle.api.initialization.resolve.MutableVersionCatalogContainer

fun String.toCamelCase(): String = this
    .lowercase()
    .replace(Regex("[\\s-_]+([a-z])")) { it.groupValues[1].uppercase() }

fun Settings.createBy(
    versionCatalogs: MutableVersionCatalogContainer,
    vararg targets: String,
) = targets.forEach { createBy(versionCatalogs, it) }

fun Settings.createBy(
    versionCatalogs: MutableVersionCatalogContainer,
    target: String,
) {
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

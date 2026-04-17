package com.makeboon.gradle.extension

import org.gradle.api.Project
import org.gradle.api.initialization.Settings
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByName

public val DefaultVersionCatalogNames: List<String> = listOf(
    "build-logic", "kotlinx", "makeboon",
    "kmp", "kmp-ext",
    "kmp-android", "kmp-apple",
    "kmp-application",
)

public fun String.toCamelCase(): String =
    lowercase()
        .replace(Regex("[\\s-_]+([a-z])")) {
            it.groupValues[1].uppercase()
        }

public fun Settings.createProjectVersionCatalogs() {
    createVersionCatalogs(*DefaultVersionCatalogNames.toTypedArray())
}

public val Project.librariesForAll: List<AbstractExternalDependencyFactory>
    get() = DefaultVersionCatalogNames.map { it.toCamelCase() }
        .map { extensions.getByName<AbstractExternalDependencyFactory>(it) }

public fun Project.implementationDefaultVersionCatalogLibraries() {
    dependencies {
        // TODO: workaround for accessing version-catalog in convention plugins
        // https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
        librariesForAll.forEach {
            "implementation"(files(it.javaClass.superclass.protectionDomain.codeSource.location))
        }
    }
}

package com.makeboon.gradle.extension

import org.gradle.accessors.dm.*
import org.gradle.api.Project
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

public fun Project.implementationDefaultVersionCatalogLibraries() {
    dependencies {
        // TODO: workaround for accessing version-catalog in convention plugins
        // https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
        librariesForAll.forEach {
            "implementation"(files(it.javaClass.superclass.protectionDomain.codeSource.location))
        }
    }
}

public val Project.librariesForAll: List<AbstractExternalDependencyFactory>
    get() = listOf(
        buildLogic, makeboon,
        kmp, kmpExt,
        kmpAndroid, kmpIos,
        kmpApp
    )
public val Project.buildLogic: LibrariesForBuildLogic get() = extensions.getByType()
public val Project.makeboon: LibrariesForMakeboon get() = extensions.getByType()
public val Project.kmp: LibrariesForKmp get() = extensions.getByType()
public val Project.kmpExt: LibrariesForKmpExt get() = extensions.getByType()
public val Project.kmpAndroid: LibrariesForKmpAndroid get() = extensions.getByType()
public val Project.kmpIos: LibrariesForKmpIos get() = extensions.getByType()
public val Project.kmpApp: LibrariesForKmpApp get() = extensions.getByType()

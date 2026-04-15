package com.makeboon.gradle.extension

import org.gradle.accessors.dm.*
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

public val Project.buildLogic: LibrariesForBuildLogic get() = extensions.getByType()
public val Project.makeboon: LibrariesForMakeboon get() = extensions.getByType()
public val Project.kmp: LibrariesForKmp get() = extensions.getByType()
public val Project.kmpExt: LibrariesForKmpExt get() = extensions.getByType()
public val Project.kmpAndroid: LibrariesForKmpAndroid get() = extensions.getByType()
public val Project.kmpIos: LibrariesForKmpIos get() = extensions.getByType()
public val Project.kmpApp: LibrariesForKmpApp get() = extensions.getByType()

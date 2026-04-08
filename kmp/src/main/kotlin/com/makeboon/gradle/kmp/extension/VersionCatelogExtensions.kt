package com.makeboon.gradle.kmp.extension

import org.gradle.accessors.dm.LibrariesForCore
import org.gradle.accessors.dm.LibrariesForKmp
import org.gradle.accessors.dm.LibrariesForKmpAndroid
import org.gradle.accessors.dm.LibrariesForKmpIos
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

public val Project.core: LibrariesForCore get() = extensions.getByType()
public val Project.kmp: LibrariesForKmp get() = extensions.getByType()
public val Project.kmpAndroid: LibrariesForKmpAndroid get() = extensions.getByType()
public val Project.kmpIos: LibrariesForKmpIos get() = extensions.getByType()

package com.makeboon.gradle.extension

import org.gradle.api.file.Directory
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import java.io.File
import java.util.*
import org.gradle.api.artifacts.MinimalExternalModuleDependency as Dependency

public fun `-opt-in`(vararg api: String): List<String> = api.map { "-opt-in=$it" }
public fun `-X`(vararg api: String): List<String> = api.map { "-X$it" }

public val String.asFile: File get() = File(this)
public fun String.toFile(parent: String): File = File(parent, this)
public val Directory.files: List<File> get() = asFileTree.files.toList()
public val Directory.regularFiles: List<RegularFile> get() = asFileTree.map { file(it.path) }
public fun Directory.regularFilesInDir(path: String): List<RegularFile> = dir(path).regularFiles
public fun File.toProperties(): Properties = Properties().apply { inputStream().use(::load) }
public fun Properties.getPropertyInt(key: String): Int = getPropertyIntOrNull(key)!!
public fun Properties.getPropertyIntOrNull(key: String): Int? = getProperty(key)?.toInt()
public fun Properties.getPropertyBoolean(key: String): Boolean = getPropertyBooleanOrNull(key)!!
public fun Properties.getPropertyBooleanOrNull(key: String): Boolean? = getProperty(key)?.toBoolean()

public fun KotlinDependencyHandler.bom(dependencyProvider: Provider<Dependency>): org.gradle.api.artifacts.Dependency? =
    api(project.dependencies.platform(dependencyProvider))

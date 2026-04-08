package com.makeboon.gradle.extension

import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.file.RegularFile
import org.gradle.api.plugins.PluginManager
import org.gradle.api.provider.Provider
import org.gradle.plugin.use.PluginDependency
import java.io.File

public val Project.moduleNamespace: String
    get() = "$group.$name"

public fun PluginManager.apply(provider: Provider<PluginDependency>): Unit =
    apply(provider.get().pluginId)

public fun Project.hasPlugin(provider: Provider<PluginDependency>): Boolean =
    plugins.hasPlugin(provider.get().pluginId)

public fun Project.dirProviderInBuildDir(path: String): Provider<Directory> =
    layout.buildDirectory.dir(path)

public fun Project.fileProviderInBuildDir(path: String): Provider<RegularFile> =
    layout.buildDirectory.file(path)

public fun Project.dirInBuildDir(path: String): Directory =
    dirProviderInBuildDir(path).get()

public fun Project.regularFileInBuildDir(path: String): RegularFile =
    fileProviderInBuildDir(path).get()

public fun Project.fileInBuildDir(path: String): File =
    regularFileInBuildDir(path).asFile

public fun Project.dirInProjectDir(path: String): Directory =
    layout.projectDirectory.dir(path)

public fun Project.regularFileInProjectDir(path: String): RegularFile =
    layout.projectDirectory.file(path)

public fun Project.regularFilesInProjectDir(path: String): List<RegularFile> =
    dirInProjectDir(path).regularFiles

public fun Project.fileInProjectDir(path: String): File =
    regularFileInProjectDir(path).asFile

public fun Project.filesInProjectDir(path: String): List<File> =
    dirInProjectDir(path).files.toList()

public fun Project.dirInRootDir(path: String): Directory =
    rootProject.dirInProjectDir(path)

public fun Project.regularFileInRootDir(path: String): RegularFile =
    rootProject.regularFileInProjectDir(path)

public fun Project.regularFilesInRootDir(path: String): List<RegularFile> =
    rootProject.regularFilesInProjectDir(path)

public fun Project.fileInRootDir(path: String): File =
    rootProject.fileInProjectDir(path)

public fun Project.filesInRootDir(path: String): List<File> =
    rootProject.filesInProjectDir(path)

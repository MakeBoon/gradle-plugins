package pro.crestfi.gradle

import org.gradle.api.GradleException
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.file.Directory
import org.gradle.api.provider.Provider
import org.gradle.internal.extensions.stdlib.capitalized
import org.gradle.kotlin.dsl.getByType
import org.gradle.plugin.devel.PluginDeclaration
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import java.io.File
import java.io.InputStream
import java.net.URL
import java.util.*
import kotlin.io.path.createTempFile
import kotlin.jvm.optionals.getOrElse
import kotlin.reflect.KClass
import org.gradle.api.artifacts.ExternalModuleDependencyBundle as Bundle
import org.gradle.api.artifacts.MinimalExternalModuleDependency as Dependency

val Project.libs: VersionCatalog
    get(): VersionCatalog = with(extensions.getByType<VersionCatalogsExtension>()) {
        find("core").getOrElse { named("libs") }
    }
val Project.publishName get() = path.drop(1).replace(':', '.')

val Directory.regularFiles get() = asFileTree.map { file(it.path) }
fun Directory.regularFilesInDir(path: String) = dir(path).regularFiles

fun <T> VersionCatalog.kindly(alias: String, block: VersionCatalog.(String) -> T): T =
    try {
        block(alias)
    } catch (e: NoSuchElementException) {
        throw GradleException("alias: `$alias` not found", e)
    }

fun VersionCatalog.pluginId(alias: String): String = kindly(alias) { findPlugin(it).get().get().pluginId }
fun VersionCatalog.version(alias: String): String = kindly(alias) { findVersion(it).get().requiredVersion }
fun VersionCatalog.versionInt(alias: String): Int = kindly(alias) { version(it).toInt() }
fun VersionCatalog.versionIntOrNull(alias: String): Int? = kindly(alias) { version(it).toIntOrNull() }
fun VersionCatalog.library(alias: String): Provider<Dependency> = kindly(alias) { findLibrary(it).get() }
fun VersionCatalog.libraryId(alias: String): String = kindly(alias) { library(it).get().name }
fun VersionCatalog.bundle(alias: String): Provider<Bundle> = kindly(alias) { findBundle(it).get() }
fun VersionCatalog.dependency(alias: String): Dependency = kindly(alias) { library(it).get() }

val String.asFile get() = File(this)
fun String.toFile(parent: String) = File(parent, this)
fun File.with(append: String) = File(path, append)
fun File.toProperties() = Properties().apply { inputStream().use(::load) }

fun KotlinDependencyHandler.bom(dependencyProvider: Provider<Dependency>) =
    api(project.dependencies.platform(dependencyProvider))

fun NamedDomainObjectContainer<PluginDeclaration>.registerOf(
    project: Project,
    name: String,
    suffix: Boolean = true,
    pluginName: ((String) -> String)? = null,
) {
    with(project) {
        val groupName = "$group.$publishName"
        val lowercasedName = name.lowercase()
        val packageName = buildString {
            append(groupName)
            if (suffix) append(".gradle")
        }
        val pluginClassName = pluginName?.invoke(name) ?: name.split('-').joinToString("", transform = String::capitalized)

        register(lowercasedName) {
            id = "$groupName.gradle.$lowercasedName"
            implementationClass = "$packageName.${pluginClassName}Plugin"
        }
    }
}

fun KClass<*>.resource(vararg paths: String): URL? =
    java.getResource("/${paths.joinToString("/")}")

fun KClass<*>.resourceStream(vararg paths: String): InputStream? =
    java.getResourceAsStream("/${paths.joinToString("/")}")

fun KClass<*>.fileFromResource(
    vararg paths: String,
    target: () -> File = { createTempFile().toFile() },
): File? = resourceStream(*paths)?.let { input ->
    target().apply {
        deleteOnExit()
        outputStream().use { input.copyTo(it) }
    }
}

fun `-opt-in`(vararg api: String) = api.map { "-opt-in=$it" }
fun `-X`(vararg api: String) = api.map { "-X$it" }

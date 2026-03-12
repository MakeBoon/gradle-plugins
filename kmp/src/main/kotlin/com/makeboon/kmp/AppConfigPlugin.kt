package com.makeboon.kmp

import com.makeboon.gradle.getPropertyBooleanOrNull
import com.makeboon.gradle.getPropertyInt
import com.makeboon.gradle.toProperties
import com.makeboon.kmp.AppConfigExtension.Companion.EXTENSION_NAME
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

class AppConfigPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val appConfig = extensions.create(EXTENSION_NAME, AppConfigExtension::class).apply {
            with(layout.projectDirectory.file("../AppConfig.xcconfig").asFile.toProperties()) {
                projectNamespace = getProperty("ProjectNamespace")
                versionMajor = getPropertyInt("VersionMajor")
                versionMinor = getPropertyInt("VersionMinor")
                versionPatch = getPropertyInt("VersionPatch")
                versionBuild = getPropertyInt("VersionBuild")
                paymentTest = getPropertyBooleanOrNull("PaymentTest") ?: false
            }
        }
        with(appConfig) {
            group = projectNamespace
            version = versionNameWithBuild
        }
    }
}

abstract class AppConfigExtension {
    companion object Companion {
        const val EXTENSION_NAME = "appConfig"
    }

    abstract var projectNamespace: String
    abstract var versionMajor: Int
    abstract var versionMinor: Int
    abstract var versionPatch: Int
    abstract var versionBuild: Int
    abstract var paymentTest: Boolean
    val versionCode: Int get() = versionMajor * 1_000_000 + versionMinor * 10_000 + versionPatch * 100 + versionBuild
    val versionName: String get() = "$versionMajor.$versionMinor.$versionPatch"
    val versionNameWithBuild: String get() = "$versionName.$versionBuild"
}
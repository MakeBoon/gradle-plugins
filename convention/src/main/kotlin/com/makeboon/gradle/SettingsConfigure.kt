package com.makeboon.gradle

import com.makeboon.gradle.extension.create
import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings

public object SettingsConfigure : Plugin<Settings> {
    override fun apply(settings: Settings) {
        with(settings) {
            pluginManagement.plugins {
                id("org.gradle.toolchains.foojay-resolver-convention").version("1.0.0")
            }
            versionCatalogs()
        }
    }

    private fun Settings.versionCatalogs() {
        dependencyResolutionManagement {
            repositories {
                mavenCentral()
                google()
                gradlePluginPortal()
            }
            versionCatalogs.create(
                "core" to "build-plugins",
                "makeboon" to "makeboon-plugins",
                "kmp" to "kmp",
                "kmpExt" to "kmp-ext",
                "kmpAndroid" to "kmp-android",
                "kmpIos" to "kmp-ios",
                "kmpApp" to "kmp-app",
            )
        }
    }
}
package com.makeboon.gradle.kmp.plugin

import com.github.gmazzo.buildconfig.BuildConfigExtension
import com.makeboon.gradle.extension.apply
import com.makeboon.gradle.extension.buildLogic
import com.makeboon.gradle.kmp.extension.AppConfig
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.buildConfigField
import org.gradle.kotlin.dsl.configure

internal class BuildConfigPlugin(val library: Boolean) : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(buildLogic.plugins.buildconfig)
        }

        if (library) return@with
        val appConfig = AppConfig.configure(target)
        extensions.configure<BuildConfigExtension> {
            packageName(appConfig.projectNamespace)
            buildConfigField("PRODUCT_VERSION", appConfig.versionName)
            buildConfigField("BUNDLE_VERSION", "${appConfig.versionCode}")
        }
    }
}

package com.makeboon.gradle

import app.cash.sqldelight.gradle.SqlDelightExtension
import com.makeboon.gradle.extension.apply
import com.makeboon.gradle.extension.buildLogic
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

public object SQLDelightPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(buildLogic.plugins.sqldelight)
        }

        extensions.configure<SqlDelightExtension> {
        }
    }
}

package com.makeboon.gradle

import com.makeboon.gradle.extension.apply
import com.makeboon.gradle.extension.buildLogic
import com.squareup.wire.gradle.WireExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

public object WirePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(buildLogic.plugins.wire)
        }

        extensions.configure<WireExtension> {
            // https://square.github.io/wire/wire_compiler/#kotlin
            kotlin {}
        }
    }
}

package com.makeboon.kmp.gradle

import com.squareup.wire.gradle.WireExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import com.makeboon.gradle.core
import com.makeboon.gradle.pluginId

class WirePlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(core.pluginId("wire"))
        }

        extensions.configure<WireExtension> {
            // https://square.github.io/wire/wire_compiler/#kotlin
            kotlin {}
        }
    }
}
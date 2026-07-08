package com.makeboon.gradle.kmp.target

import com.makeboon.gradle.extension.apply
import com.makeboon.gradle.extension.kmpExt
import com.makeboon.gradle.kmp.extension.AppConfig
import com.makeboon.gradle.kmp.extension.OptIn
import com.makeboon.gradle.kmp.plugin.ComposeFlattenDrawableResourcePlugin
import com.makeboon.gradle.kmp.plugin.ComposePlugin
import com.makeboon.gradle.kmp.plugin.FrameworkPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

public object ModulePlugin {
    public fun apply(
        target: Project,
        library: Boolean,
        compose: Boolean,
        publish: Boolean,
        vararg targets: TargetPlugin,
    ): Unit = with(target) {
        if (!library) AppConfig.configure(this)

        with(pluginManager) {
            if (publish) // should be the first plugin. group, name.
                apply("com.makeboon.gradle.publish")

            listOf(
                "com.makeboon.gradle.ksp",
            ).forEach(::apply)

            mutableListOf<Plugin<Project>>().apply {
                addAll(
                    listOf(
                        FrameworkPlugin(library),
                        *targets
                    )
                )

                if (compose) {
                    addAll(
                        listOf(
                            ComposePlugin,
                            ComposeFlattenDrawableResourcePlugin
                        )
                    )
                }
            }.forEach { it.apply(target) }

            listOf(
                "com.makeboon.gradle.wire",
//                "com.makeboon.gradle.sqldelight",
                "com.makeboon.gradle.kmp.room3",
            ).forEach(::apply)

            listOf(
                kmpExt.plugins.metro
            ).forEach { this.apply(it) }

            OptIn.configure(target, library, compose)
        }
    }
}
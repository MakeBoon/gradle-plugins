package com.makeboon.gradle.kmp

import com.makeboon.gradle.kmp.extension.AppConfig
import com.makeboon.gradle.kmp.extension.OptIn
import com.makeboon.gradle.kmp.extension.iosMacosTargets
import org.gradle.api.Plugin
import org.gradle.api.Project

public object ModulePlugin {
    public fun apply(
        target: Project,
        library: Boolean,
        compose: Boolean,
        publish: Boolean,
    ): Unit = with(target) {
        if (!library) AppConfig.configure(this)

        with(pluginManager) {
            if (publish) // should be the first plugin. group, name.
                apply("com.makeboon.gradle.publish")

            mutableListOf<Plugin<Project>>().apply {
                addAll(
                    listOf(
                        FrameworkPlugin(library),
                        AndroidTargetPlugin,
                        AppleTargetPlugin { iosMacosTargets },
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
                "com.makeboon.gradle.sqldelight"
            ).forEach(::apply)

            Room3Plugin.apply(target)

            OptIn.configure(target, library, compose)
        }
    }
}

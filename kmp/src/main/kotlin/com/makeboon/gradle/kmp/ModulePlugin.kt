package com.makeboon.gradle.kmp

import com.makeboon.gradle.SQLDelightPlugin
import com.makeboon.gradle.WirePlugin
import com.makeboon.gradle.kmp.extension.AppConfig
import com.makeboon.gradle.kmp.extension.OptIn
import com.makeboon.gradle.kmp.extension.iosTargets
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
                        AppleTargetPlugin { iosTargets },
                        DesktopTargetPlugin,
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
                WirePlugin,
                SQLDelightPlugin,
                Room3Plugin,
            ).forEach { it.apply(target) }

            OptIn.configure(target, library, compose)
        }
    }
}

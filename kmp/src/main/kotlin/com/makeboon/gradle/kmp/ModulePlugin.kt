package com.makeboon.gradle.kmp

import com.makeboon.gradle.kmp.extension.AppConfig
import com.makeboon.gradle.kmp.extension.OptIn
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
                apply("com.makeboon.gradle.kmp.publish")

            mutableListOf<Plugin<Project>>().apply {
                addAll(
                    listOf(
                        FrameworkPlugin(library),
                        AndroidTargetPlugin(),
                        iOSTargetPlugin(),
                    )
                )

                if (compose) {
                    addAll(
                        listOf(
                            ComposePlugin(),
                            ComposeFlattenDrawableResourcePlugin()
                        )
                    )
                }
            }.forEach { it.apply(target) }

            apply("com.makeboon.gradle.kmp.wire")

            OptIn.configure(target, library, compose)
        }
    }
}

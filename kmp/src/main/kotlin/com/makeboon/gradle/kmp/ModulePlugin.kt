package com.makeboon.gradle.kmp

import com.makeboon.gradle.kmp.extension.AppConfig
import com.makeboon.gradle.kmp.extension.OptIn
import com.makeboon.gradle.kmp.extension.iosTargets
import org.gradle.api.Plugin
import org.gradle.api.Project

public object ModulePlugin {
    /** Set `makeboon.kmp.wasmJs=true` in gradle.properties to add a wasmJs browser target. */
    private val Project.wasmJs: Boolean
        get() = findProperty("makeboon.kmp.wasmJs")?.toString().toBoolean()

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

            listOf(
                "com.makeboon.gradle.ksp",
            ).forEach(::apply)

            mutableListOf<Plugin<Project>>().apply {
                addAll(
                    listOf(
                        FrameworkPlugin(library),
                        AndroidTargetPlugin,
                        AppleTargetPlugin { iosTargets },
//                        DesktopTargetPlugin,
                    )
                )

                if (wasmJs) add(WasmJsTargetPlugin(application = !library))

                if (compose) {
                    addAll(
                        listOf(
                            ComposePlugin,
                            ComposeFlattenDrawableResourcePlugin
                        )
                    )
                }
            }.forEach { it.apply(target) }

            mutableListOf(
                "com.makeboon.gradle.wire",
                "com.makeboon.gradle.sqldelight",
            ).apply {
                // room3.gradle.kts puts the room3 bundle in commonMain,
                // but Room3 does not publish wasm artifacts.
                if (!wasmJs) add("com.makeboon.gradle.kmp.room3")
            }.forEach(::apply)

            OptIn.configure(target, library, compose)
        }
    }
}

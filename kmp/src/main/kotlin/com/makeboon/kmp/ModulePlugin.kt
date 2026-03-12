package com.makeboon.kmp

import org.gradle.api.Plugin
import org.gradle.api.Project
import com.makeboon.kmp.gradle.AndroidTargetPlugin
import com.makeboon.kmp.gradle.ComposeFlattenDrawableResourcePlugin
import com.makeboon.kmp.gradle.ComposePlugin
import com.makeboon.kmp.gradle.ComposePreviewPlugin
import com.makeboon.kmp.gradle.FrameworkPlugin
import com.makeboon.kmp.gradle.KSPPlugin
import com.makeboon.kmp.gradle.PublishPlugin
import com.makeboon.kmp.gradle.RoomPlugin
import com.makeboon.kmp.gradle.WirePlugin
import com.makeboon.kmp.gradle.iOSTargetPlugin

class ComposeApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) =
        ModulePlugin(
            compose = true,
            library = false,
            publish = false
        ).apply(target)
}

class ComposeLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) =
        ModulePlugin(
            compose = true,
            library = true,
            publish = true
        ).apply(target)
}

class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) =
        ModulePlugin(
            compose = false,
            library = true,
            publish = true
        ).apply(target)
}

private class ModulePlugin(
    private val compose: Boolean,
    private val library: Boolean,
    private val publish: Boolean,
) : Plugin<Project> {
    override fun apply(target: Project) {
        mutableListOf<Plugin<Project>>().apply {
            if (!library) add(AppConfigPlugin())
            addAll(
                listOf(
                    FrameworkPlugin(library),
                    KSPPlugin(),
                    WirePlugin(),
                    RoomPlugin(),
                )
            )
            if (compose) {
                addAll(
                    listOf(
                        ComposePlugin(),
                        ComposePreviewPlugin(),
                        ComposeFlattenDrawableResourcePlugin()
                    )
                )
            }
            //
            addAll(
                listOf(
                    AndroidTargetPlugin(),
                    iOSTargetPlugin(library),
                    OptInPlugin(compose, library),
                )
            )
            if (publish) add(PublishPlugin())
        }.forEach { it.apply(target) }
    }
}
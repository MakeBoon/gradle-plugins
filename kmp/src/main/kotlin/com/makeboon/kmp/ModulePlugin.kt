package com.makeboon.kmp

import com.makeboon.kmp.gradle.*
import org.gradle.api.Plugin
import org.gradle.api.Project

class ComposeApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) =
        ModulePlugin(
            application = true,
            compose = true,
            library = false,
            publish = false
        ).apply(target)
}

class ApplicationComposeLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) =
        ModulePlugin(
            application = true,
            compose = true,
            library = true,
            publish = false
        ).apply(target)
}

class ApplicationLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) =
        ModulePlugin(
            application = true,
            compose = false,
            library = true,
            publish = false
        ).apply(target)
}

class ComposeLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) =
        ModulePlugin(
            application = false,
            compose = true,
            library = true,
            publish = true
        ).apply(target)
}

class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) =
        ModulePlugin(
            application = false,
            compose = false,
            library = true,
            publish = true
        ).apply(target)
}

private class ModulePlugin(
    private val application: Boolean,
    private val compose: Boolean,
    private val library: Boolean,
    private val publish: Boolean,
) : Plugin<Project> {
    override fun apply(target: Project) {
        mutableListOf<Plugin<Project>>().apply {
            if (application) add(AppConfigPlugin())
            addAll(
                listOf(
                    FrameworkPlugin(library),
                    KSPPlugin(),
                    WirePlugin(),
                    Room3Plugin(),
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
package pro.crestfi.kmp

import org.gradle.api.Plugin
import org.gradle.api.Project
import pro.crestfi.kmp.gradle.AndroidTargetPlugin
import pro.crestfi.kmp.gradle.ComposeFlattenDrawableResourcePlugin
import pro.crestfi.kmp.gradle.ComposePlugin
import pro.crestfi.kmp.gradle.ComposePreviewPlugin
import pro.crestfi.kmp.gradle.FrameworkPlugin
import pro.crestfi.kmp.gradle.KSPPlugin
import pro.crestfi.kmp.gradle.PublishPlugin
import pro.crestfi.kmp.gradle.RoomPlugin
import pro.crestfi.kmp.gradle.WirePlugin
import pro.crestfi.kmp.gradle.iOSTargetPlugin

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
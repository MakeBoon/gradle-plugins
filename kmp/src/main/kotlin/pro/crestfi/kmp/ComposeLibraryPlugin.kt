package pro.crestfi.kmp

import org.gradle.api.Plugin
import org.gradle.api.Project
import pro.crestfi.kmp.gradle.AndroidLibraryPlugin
import pro.crestfi.kmp.gradle.ComposePlugin
import pro.crestfi.kmp.gradle.ComposePreviewPlugin
import pro.crestfi.kmp.gradle.FrameworkPlugin
import pro.crestfi.kmp.gradle.KSPPlugin
import pro.crestfi.kmp.gradle.PublishPlugin
import pro.crestfi.kmp.gradle.RoomPlugin
import pro.crestfi.kmp.gradle.WirePlugin
import pro.crestfi.kmp.gradle.iOSLibraryPlugin

class ComposeLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        listOf(
            FrameworkPlugin(),
            ComposePlugin(),
            KSPPlugin(),
            WirePlugin(),
            RoomPlugin(),
            //
            ComposePreviewPlugin(),
            AndroidLibraryPlugin(),
            iOSLibraryPlugin(),
            OptInPlugin(compose = true, library = true),
            //
            PublishPlugin(),
        ).forEach { it.apply(target) }
    }
}
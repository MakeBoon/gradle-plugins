package pro.crestfi.kmp

import org.gradle.api.Plugin
import org.gradle.api.Project
import pro.crestfi.kmp.gradle.AndroidLibraryPlugin
import pro.crestfi.kmp.gradle.FrameworkPlugin
import pro.crestfi.kmp.gradle.KSPPlugin
import pro.crestfi.kmp.gradle.PublishPlugin
import pro.crestfi.kmp.gradle.RoomPlugin
import pro.crestfi.kmp.gradle.WirePlugin
import pro.crestfi.kmp.gradle.iOSLibraryPlugin

class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        listOf(
            FrameworkPlugin(),
            KSPPlugin(),
            WirePlugin(),
            RoomPlugin(),
            //
            AndroidLibraryPlugin(),
            iOSLibraryPlugin(),
            OptInPlugin(compose = false, library = true),
            //
            PublishPlugin(),
        ).forEach { it.apply(target) }
    }
}
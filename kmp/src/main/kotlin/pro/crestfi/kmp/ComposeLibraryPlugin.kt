package pro.crestfi.kmp

import org.gradle.api.Plugin
import org.gradle.api.Project
import pro.crestfi.kmp.gradle.*

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
            OptInPlugin(true),
            //
            PublishPlugin(),
        ).forEach { it.apply(target) }
    }
}
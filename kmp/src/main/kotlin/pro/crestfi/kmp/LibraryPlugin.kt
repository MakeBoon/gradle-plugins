package pro.crestfi.kmp

import org.gradle.api.Plugin
import org.gradle.api.Project
import pro.crestfi.kmp.gradle.*

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
            OptInPlugin(),
            //
            PublishPlugin(),
        ).forEach { it.apply(target) }
    }
}
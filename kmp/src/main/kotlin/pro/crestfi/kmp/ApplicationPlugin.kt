package pro.crestfi.kmp

import org.gradle.api.Plugin
import org.gradle.api.Project
import pro.crestfi.kmp.gradle.*

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        listOf(
            FrameworkPlugin(true),
            ComposePlugin(),
            ComposeFlattenDrawableResourcePlugin(),
            KSPPlugin(),
            WirePlugin(),
            RoomPlugin(),
            //
            ComposePreviewPlugin(),
            AndroidApplicationPlugin(),
            OptInPlugin(true),
            //
            FirebasePlugin()
        ).forEach { it.apply(target) }
    }
}
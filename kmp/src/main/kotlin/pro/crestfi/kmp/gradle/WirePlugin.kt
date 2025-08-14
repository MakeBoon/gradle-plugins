package pro.crestfi.kmp.gradle

import com.squareup.wire.gradle.WireExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import pro.crestfi.gradle.libs
import pro.crestfi.gradle.pluginId

class WirePlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.pluginId("wire"))
        }

        extensions.configure<WireExtension> {
            // https://square.github.io/wire/wire_compiler/#kotlin
            kotlin {}
        }
    }
}
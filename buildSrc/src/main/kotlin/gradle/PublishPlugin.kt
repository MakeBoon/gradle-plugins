package gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.plugin.devel.GradlePluginDevelopmentExtension
import pro.crestfi.gradle.PROJECT_GROUP_ID
import pro.crestfi.gradle.PROJECT_VERSION
import pro.crestfi.gradle.libs
import pro.crestfi.gradle.pluginId

class PublishPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("maven-publish")
            apply("java-gradle-plugin")
            apply(libs.pluginId("gradle-publish"))
        }

        group = PROJECT_GROUP_ID
        version = PROJECT_VERSION

        extensions.configure<GradlePluginDevelopmentExtension>() {
            val url = "https://github.com/ib-fi/gradle-plugins"
            website.set(url)
            vcsUrl.set(url)
        }
    }
}
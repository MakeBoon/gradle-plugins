package gradle

import com.makeboon.gradle.PROJECT_GROUP_ID
import com.makeboon.gradle.PROJECT_VERSION
import com.makeboon.gradle.core
import com.makeboon.gradle.pluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.plugin.devel.GradlePluginDevelopmentExtension

class PublishPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("maven-publish")
            apply("java-gradle-plugin")
            apply(core.pluginId("gradle-publish"))
        }

        group = PROJECT_GROUP_ID
        version = PROJECT_VERSION

        extensions.configure<GradlePluginDevelopmentExtension> {
            val url = "https://github.com/MakeBoon/gradle-plugins"
            website.set(url)
            vcsUrl.set(url)
        }
    }
}
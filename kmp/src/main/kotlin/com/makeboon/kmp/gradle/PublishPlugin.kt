package com.makeboon.kmp.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class PublishPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("maven-publish")
        }
    }
}
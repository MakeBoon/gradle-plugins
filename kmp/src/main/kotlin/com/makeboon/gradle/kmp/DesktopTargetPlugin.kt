package com.makeboon.gradle.kmp

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

public object DesktopTargetPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        extensions.configure<KotlinMultiplatformExtension> {
            jvm()
        }
    }
}

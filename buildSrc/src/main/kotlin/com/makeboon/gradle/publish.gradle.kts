package com.makeboon.gradle

import com.vanniktech.maven.publish.GradlePublishPlugin
import org.gradle.accessors.dm.LibrariesForCore
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

val core = the<LibrariesForCore>()

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.dokka")
    id("com.vanniktech.maven.publish")
    id("com.gradle.plugin-publish")
}

kotlin {
    jvmToolchain(core.versions.kotlin.jvmToolchain.get().toInt())
    explicitApi()
    @OptIn(ExperimentalAbiValidation::class)
    abiValidation { enabled = true }
    compilerOptions {
        val kotlinVersion = KotlinVersion.fromVersion(core.versions.kotlin.compile.get())
        languageVersion.set(kotlinVersion)
        apiVersion.set(kotlinVersion)
        progressiveMode.set(true)
        freeCompilerArgs.addAll(
            "-Xcontext-parameters"
        )
    }
}

dependencies {
    implementation(core.gradlePlugin.kotlin.api)
}

mavenPublishing {
    configure(GradlePublishPlugin())
}
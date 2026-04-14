package com.makeboon

import org.gradle.accessors.dm.LibrariesForCore
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

val core = the<LibrariesForCore>()

plugins {
    id("com.gradle.plugin-publish")
    id("org.jetbrains.kotlin.jvm")
    id("com.makeboon.dokka")
    id("com.vanniktech.maven.publish")
}

kotlin {
    jvmToolchain(core.versions.kotlin.jvmToolchain.get().toInt())
    explicitApi()
    @OptIn(ExperimentalAbiValidation::class)
    abiValidation { enabled = true }
    compilerOptions {
        val kotlinVersion = KotlinVersion.fromVersion(core.versions.kotlin.compile.get())
        languageVersion = kotlinVersion
        apiVersion = kotlinVersion
        progressiveMode = true
        freeCompilerArgs.addAll(
            "-Xcontext-parameters"
        )
    }
}

dependencies {
    implementation(core.gradlePlugin.kotlin.api)
}

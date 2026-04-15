package com.makeboon.gradle

import org.gradle.accessors.dm.LibrariesForBuildLogic
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

val buildLogic = the<LibrariesForBuildLogic>()

plugins {
    id("com.makeboon.gradle.artifact")
    id("com.gradle.plugin-publish")
    id("org.jetbrains.kotlin.jvm")
    id("com.github.gmazzo.buildconfig")
    id("org.jetbrains.dokka")
    id("com.vanniktech.maven.publish")
}

kotlin {
    jvmToolchain(buildLogic.versions.kotlin.jvmToolchain.get().toInt())
    explicitApi()
    @OptIn(ExperimentalAbiValidation::class)
    abiValidation { enabled = true }
    compilerOptions {
        val kotlinVersion = KotlinVersion.fromVersion(buildLogic.versions.kotlin.compile.get())
        languageVersion = kotlinVersion
        apiVersion = kotlinVersion
        progressiveMode = true
        freeCompilerArgs.addAll()
    }
}

dependencies {
    implementation(buildLogic.gradlePlugin.kotlin.api)
}

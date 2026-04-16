package com.makeboon.gradle

import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

plugins {
    id("com.makeboon.gradle.artifact")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
}

kotlin {
    jvmToolchain(21)
    explicitApi()
    @OptIn(ExperimentalAbiValidation::class)
    abiValidation { enabled = true }
    compilerOptions {
        val kotlinVersion = KotlinVersion.KOTLIN_2_4
        languageVersion = kotlinVersion
        apiVersion = kotlinVersion
        progressiveMode = true
        freeCompilerArgs.addAll()
    }
}

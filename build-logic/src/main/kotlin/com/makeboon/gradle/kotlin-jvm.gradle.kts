package com.makeboon.gradle

import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.github.gmazzo.buildconfig")
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        val kotlinVersion = KotlinVersion.KOTLIN_2_4
        languageVersion = kotlinVersion
        apiVersion = kotlinVersion
        progressiveMode = true
        freeCompilerArgs.addAll()
    }
}

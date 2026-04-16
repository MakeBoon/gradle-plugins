package com.makeboon.gradle

import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

plugins {
    id("com.makeboon.gradle.kotlin-jvm")
    id("org.jetbrains.kotlinx.binary-compatibility-validator")
    id("com.gradle.plugin-publish")
    id("com.github.gmazzo.buildconfig")
    id("org.jetbrains.dokka")
    id("com.vanniktech.maven.publish")
}

kotlin {
    explicitApi()
    @OptIn(ExperimentalAbiValidation::class)
    abiValidation { enabled = true }
}

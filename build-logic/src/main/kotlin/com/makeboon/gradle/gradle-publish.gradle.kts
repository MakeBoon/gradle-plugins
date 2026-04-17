package com.makeboon.gradle

import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

plugins {
    id("com.gradle.plugin-publish")
    id("com.makeboon.gradle.kotlin-jvm")
    id("com.makeboon.gradle.publish")
}

kotlin {
    explicitApi()
    @OptIn(ExperimentalAbiValidation::class)
    abiValidation { enabled = true }
}

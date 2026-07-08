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
    abiValidation()
}

gradlePlugin {
    website = "https://github.com/MakeBoon/gradle-plugins"
    vcsUrl = "https://github.com/MakeBoon/gradle-plugins.git"

    // Plugin marker POMs reuse each declaration's displayName/description,
    // both of which Maven Central requires; precompiled script plugins are
    // registered without them, so fill in defaults here.
    plugins.configureEach {
        if (displayName.isNullOrBlank()) {
            displayName = "MakeBoon $name Gradle plugin"
        }
        if (description.isNullOrBlank()) {
            description = findProperty("POM_DESCRIPTION")?.toString()
                ?: "MakeBoon convention Gradle plugin: $id"
        }
        tags.addAll("makeboon", "convention", project.name)
    }
}

afterEvaluate {
    tasks.named("publishPlugins") {
        dependsOn(tasks.named("checkKotlinAbi"))
    }
}

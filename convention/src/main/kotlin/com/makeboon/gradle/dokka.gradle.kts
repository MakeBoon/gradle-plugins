package com.makeboon.gradle

plugins {
    id("com.makeboon.gradle.artifact")
    id("org.jetbrains.dokka")
}

dependencies {
    dokkaHtmlPlugin("org.jetbrains.dokka:versioning-plugin")
}

private val outputDir = rootDir.resolve("docs/html")

dokka {
    moduleName = rootProject.name
    pluginsConfiguration {
        versioning {
            version = "${project.version}"
            olderVersionsDir = outputDir
        }
    }
    dokkaPublications.html {
        suppressInheritedMembers = true
        failOnWarning = !plugins.hasPlugin("com.gradle.plugin-publish")
        outputDirectory = outputDir.resolve("$version")
    }

    dokkaSourceSets.configureEach {
    }
}

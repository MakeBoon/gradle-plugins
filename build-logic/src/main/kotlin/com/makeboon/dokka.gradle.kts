package com.makeboon

plugins {
    id("com.makeboon.artifact")
    id("org.jetbrains.dokka")
}

dependencies {
    dokkaHtmlPlugin("org.jetbrains.dokka:versioning-plugin")
}

val outputDir = rootDir.resolve("docs/html")

dokka {
    pluginsConfiguration {
        versioning {
            version = "${project.version}"
            olderVersionsDir = outputDir
        }
    }
    dokkaPublications.html {
        suppressInheritedMembers = true
//        failOnWarning = true
        outputDirectory = outputDir.resolve("$version")
    }

    dokkaSourceSets.configureEach {
    }
}

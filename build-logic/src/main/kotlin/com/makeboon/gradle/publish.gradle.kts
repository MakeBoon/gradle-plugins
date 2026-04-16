package com.makeboon.gradle

import org.gradle.api.publish.maven.tasks.PublishToMavenLocal
import org.gradle.api.publish.maven.tasks.PublishToMavenRepository
import org.gradle.kotlin.dsl.withType

plugins {
    id("com.makeboon.gradle.artifact")
    id("org.jetbrains.kotlinx.binary-compatibility-validator")
    id("com.github.gmazzo.buildconfig")
    id("org.jetbrains.dokka")
    id("com.vanniktech.maven.publish")
}

tasks.withType<PublishToMavenRepository> {
    dependsOn(tasks.named("apiCheck"))
}

tasks.withType<PublishToMavenLocal> {
    dependsOn(tasks.named("apiCheck"))
}

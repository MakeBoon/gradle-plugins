package com.makeboon.gradle.kmp

plugins {
    id("org.jetbrains.dokka")
    id("com.vanniktech.maven.publish")
}

// gradle.properties
group = ext["GROUP"]!!
version = ext["VERSION_NAME"]!!

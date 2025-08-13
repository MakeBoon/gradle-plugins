import pro.crestfi.gradle.registerOf

plugins {
    `kotlin-dsl`
    alias(libs.plugins.convention.jvmToolchain)
    alias(libs.plugins.convention.publish)
}

dependencies {
    implementation(projects.convention)
    compileOnly(libs.gradlePlugin.compose)
    compileOnly(libs.gradlePlugin.compose.compiler)
    compileOnly(libs.gradlePlugin.ksp)
    compileOnly(libs.gradlePlugin.wire)
    compileOnly(libs.gradlePlugin.room)
    compileOnly(libs.gradlePlugin.android)
    compileOnly(libs.gradlePlugin.android.tools)
}

gradlePlugin {
    plugins {
        registerOf(project, "application", false)
        registerOf(project, "library", false)
        registerOf(project, "compose-library", false)
        //
        registerOf(project, "framework")
        registerOf(project, "compose")
        registerOf(project, "compose-flatten-drawable-resource")
        registerOf(project, "ksp") { it.uppercase() }
        registerOf(project, "wire")
        registerOf(project, "room")
        registerOf(project, "firebase")
        registerOf(project, "android-application")
        registerOf(project, "android-library")
        registerOf(project, "ios-library") { "iOSLibrary" }
        registerOf(project, "publish")
    }
}
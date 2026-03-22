import com.makeboon.gradle.registerOf

plugins {
    `kotlin-dsl`
    alias(core.plugins.convention.jvmToolchain)
    alias(core.plugins.convention.publish)
}

dependencies {
    implementation(projects.convention)
    compileOnly(core.gradlePlugin.ksp)
    compileOnly(core.gradlePlugin.wire)
    compileOnly(core.gradlePlugin.compose.compiler)
    compileOnly(kmp.gradlePlugin.compose)
    compileOnly(kmp.gradlePlugin.room3)
    compileOnly(kmpAndroid.gradlePlugin)
    compileOnly(kmpAndroid.gradlePlugin.tools)
}

gradlePlugin {
    plugins {
        registerOf(project, "app-config", false)
        registerOf(project, "compose-application", false)
        registerOf(project, "application-compose-library", false)
        registerOf(project, "application-library", false)
        registerOf(project, "compose-library", false)
        registerOf(project, "library", false)
        //
        registerOf(project, "framework")
        registerOf(project, "compose")
        registerOf(project, "compose-preview")
        registerOf(project, "compose-flatten-drawable-resource")
        registerOf(project, "ksp") { it.uppercase() }
        registerOf(project, "wire")
        registerOf(project, "room3")
        registerOf(project, "firebase")
        registerOf(project, "android-application")
        registerOf(project, "android-target")
        registerOf(project, "ios-target") { "iOSTarget" }
        registerOf(project, "publish")
    }
}
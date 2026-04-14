import com.makeboon.gradle.extension.implementationDefaultVersionCatalogLibraries

plugins {
    `kotlin-dsl`
    id("com.makeboon.gradle.gradle-publish")
}

dependencies {
    implementation(projects.convention)
    implementation(buildLogic.gradlePlugin.ksp)
    implementation(buildLogic.gradlePlugin.compose.compiler)
    implementation(kmp.gradlePlugin.compose)
//    implementation(kmp.gradlePlugin.room3)
    implementation(kmpAndroid.gradlePlugin)
    implementation(kmpAndroid.gradlePlugin.tools)

    implementationDefaultVersionCatalogLibraries()
}

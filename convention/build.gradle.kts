import com.makeboon.gradle.extension.implementationDefaultVersionCatalogLibraries

plugins {
    `kotlin-dsl`
    id("com.makeboon.gradle.gradle-publish")
}

// Version catalogs live under src/main/resources/com/makeboon/gradle/catalogs/
// so they're bundled into the jar as-is; the published settings plugin reads
// them from there without needing a Maven repository (see VersionCatalogExtensions.kt).

dependencies {
    implementation(buildLogic.gradlePlugin.foojayResolver)
    implementation(buildLogic.gradlePlugin.kotlin)
    implementation(buildLogic.gradlePlugin.binaryCompatibility)
    implementation(buildLogic.gradlePlugin.serialization)
    implementation(buildLogic.gradlePlugin.buildconfig)
    implementation(buildLogic.gradlePlugin.dokka)
    implementation(buildLogic.gradlePlugin.publish)
    implementation(support.gradlePlugin.ksp)
    implementation(support.gradlePlugin.wire)
    implementation(support.gradlePlugin.sqldelight)
    // For KSP
    implementation(kmpAndroid.gradlePlugin.api)

    implementationDefaultVersionCatalogLibraries()
}

buildConfig {
    packageName("$group")
}

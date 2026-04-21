import com.makeboon.gradle.extension.implementationDefaultVersionCatalogLibraries

plugins {
    `kotlin-dsl`
    id("com.makeboon.gradle.gradle-publish")
}

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

    implementationDefaultVersionCatalogLibraries()
}

buildConfig {
    packageName("$group")
}

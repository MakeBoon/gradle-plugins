import com.makeboon.gradle.extension.DefaultVersionCatalogNames
import com.makeboon.gradle.extension.implementationDefaultVersionCatalogLibraries

plugins {
    `kotlin-dsl`
    id("com.makeboon.gradle.gradle-publish")
}

// Bundle the version catalogs into the jar so the published settings plugin
// can register them without a Maven repository (see VersionCatalogExtensions.kt).
tasks.processResources {
    DefaultVersionCatalogNames.forEach { target ->
        from(rootDir.resolve("catalog/$target/$target.toml")) {
            into("com/makeboon/gradle/catalogs")
        }
    }
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
    // For KSP
    implementation(kmpAndroid.gradlePlugin.api)

    implementationDefaultVersionCatalogLibraries()
}

buildConfig {
    packageName("$group")
}

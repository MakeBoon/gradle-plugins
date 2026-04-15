import com.makeboon.gradle.extension.implementationDefaultVersionCatalogLibraries

plugins {
    `kotlin-dsl`
    id("com.makeboon.gradle.gradle-publish")
}

dependencies {
    implementation(buildLogic.gradlePlugin.foojayResolver)
    api(buildLogic.gradlePlugin.kotlin)
    implementation(buildLogic.gradlePlugin.serialization)
    implementation(buildLogic.gradlePlugin.buildconfig)
    implementation(buildLogic.gradlePlugin.wire)
    implementation(buildLogic.gradlePlugin.dokka)
    implementation(buildLogic.gradlePlugin.publish)

    implementationDefaultVersionCatalogLibraries()
}

buildConfig {
    packageName("$group")
    useKotlinOutput { topLevelConstants = true }

    with(project) {
        buildConfigField("LOCAL_ROOT_PROJECT_NAME", rootProject.name)
        buildConfigField("LOCAL_ROOT_DIR_NAME", rootDir.name)
        buildConfigField("GROUP_ID", "${ext["GROUP"]}")
        buildConfigField("VERSION", "${ext["VERSION_NAME"]}")
    }
}

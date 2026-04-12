plugins {
    `kotlin-dsl`
    id("com.makeboon.gradle-publish")
}

dependencies {
    implementation(core.gradlePlugin.foojayResolver)
    api(core.gradlePlugin.kotlin)
    implementation(core.gradlePlugin.serialization)
    implementation(core.gradlePlugin.wire)
    implementation(core.gradlePlugin.dokka)
    implementation(core.gradlePlugin.publish)
}

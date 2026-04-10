plugins {
    `kotlin-dsl`
    alias(core.plugins.buildSrc.publish)
}

dependencies {
    compileOnly(core.gradlePlugin.dokka)
    compileOnly(core.gradlePlugin.publish)
}

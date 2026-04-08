plugins {
    `kotlin-dsl`
    alias(core.plugins.buildSrc.publish)
}

dependencies {
    implementation(projects.convention)
    compileOnly(core.gradlePlugin.dokka)
    compileOnly(core.gradlePlugin.publish)
    compileOnly(core.gradlePlugin.ksp)
    compileOnly(core.gradlePlugin.wire)
    compileOnly(core.gradlePlugin.compose.compiler)
    compileOnly(core.gradlePlugin.serialization)
    compileOnly(core.gradlePlugin.parcelize)
    compileOnly(kmp.gradlePlugin.compose)
    compileOnly(kmp.gradlePlugin.room3)
    compileOnly(kmpAndroid.gradlePlugin)
    compileOnly(kmpAndroid.gradlePlugin.tools)

    // https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
    listOf(
        core, kmp, kmpAndroid, kmpIos,
    ).forEach {
        compileOnly(files(it.javaClass.superclass.protectionDomain.codeSource.location))
    }
}

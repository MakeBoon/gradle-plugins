plugins {
    `kotlin-dsl`
    id("com.makeboon.gradle.gradle-publish")
}

dependencies {
    implementation(projects.convention)
    implementation(core.gradlePlugin.ksp)
    implementation(core.gradlePlugin.compose.compiler)
    implementation(kmp.gradlePlugin.compose)
//    implementation(kmp.gradlePlugin.room3)
    implementation(kmpAndroid.gradlePlugin)
    implementation(kmpAndroid.gradlePlugin.tools)

    // https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
    listOf(
        core, kmp, kmpAndroid, kmpIos,
    ).forEach {
        implementation(files(it.javaClass.superclass.protectionDomain.codeSource.location))
    }
}

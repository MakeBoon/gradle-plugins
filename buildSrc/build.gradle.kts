plugins { `kotlin-dsl` }

kotlin {
    jvmToolchain(core.versions.kotlin.jvmToolchain.get().toInt())
}

dependencies {
    implementation(core.gradlePlugin.kotlin)
    implementation(core.gradlePlugin.dokka)
    implementation(core.gradlePlugin.publish)
    implementation(core.gradlePlugin.gradlePublish)

    // https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
    compileOnly(files(core.javaClass.superclass.protectionDomain.codeSource.location))
}

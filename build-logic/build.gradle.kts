plugins { `kotlin-dsl` }

kotlin {
    jvmToolchain(core.versions.kotlin.jvmToolchain.get().toInt())
}

dependencies {
    implementation(core.gradlePlugin.kotlin)
    implementation(core.gradlePlugin.dokka)
    implementation(core.gradlePlugin.publish)
    implementation(core.gradlePlugin.gradlePublish)
    implementation(core.gradlePlugin.foojayResolver)

    // workaround for accessing version-catalog in convention plugins
    // https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
    implementation(files(core.javaClass.superclass.protectionDomain.codeSource.location))
}

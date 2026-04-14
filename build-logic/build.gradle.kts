plugins { `kotlin-dsl` }

kotlin {
    jvmToolchain(buildLogic.versions.kotlin.jvmToolchain.get().toInt())
}

dependencies {
    implementation(buildLogic.gradlePlugin.kotlin)
    implementation(buildLogic.gradlePlugin.dokka)
    implementation(buildLogic.gradlePlugin.publish)
    implementation(buildLogic.gradlePlugin.gradlePublish)

    // workaround for accessing version-catalog in convention plugins
    // https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
    implementation(files(buildLogic.javaClass.superclass.protectionDomain.codeSource.location))
}

private val copyTask by tasks.registering(Copy::class) {
    val packagePath = "src/main/kotlin/com/makeboon/gradle"
    from(projectDir.resolve(packagePath))
    into(rootDir.resolve("../convention/$packagePath"))
    include(*listOf("artifact", "dokka").map { "$it.gradle.kts" }.toTypedArray())
}

tasks.compileKotlin { dependsOn(copyTask) }

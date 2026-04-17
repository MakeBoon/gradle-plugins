plugins { `kotlin-dsl` }

kotlin {
    jvmToolchain(buildLogic.versions.kotlin.jvmToolchain.get().toInt())
}

dependencies {
    implementation(buildLogic.gradlePlugin.foojayResolver)

    // TODO: workaround for accessing version-catalog in convention plugins
    // https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
    listOf(
        buildLogic, makeboon,
        kmp, kmpExt,
        kmpAndroid, kmpApple,
        kmpApplication,
    ).forEach {
        implementation(files(it.javaClass.superclass.protectionDomain.codeSource.location))
    }
}

private val copyTask by tasks.registering(Copy::class) {
    val packagePath = "src/main/kotlin/com/makeboon/gradle/extension"
    from(projectDir.resolve(packagePath))
    into(rootDir.resolve("../convention/$packagePath"))
    include(*listOf("Copy").map { "${it}Extensions.kt" }.toTypedArray())
}

tasks.compileKotlin { dependsOn(copyTask) }

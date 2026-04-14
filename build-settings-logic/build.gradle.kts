plugins { `kotlin-dsl` }

kotlin {
    jvmToolchain(buildLogic.versions.kotlin.jvmToolchain.get().toInt())
}

dependencies {
    implementation(buildLogic.gradlePlugin.foojayResolver)
}

private val copyTask by tasks.registering(Copy::class) {
    val packagePath = "src/main/kotlin/com/makeboon/gradle/extension"
    from(projectDir.resolve(packagePath))
    into(rootDir.resolve("../convention/$packagePath"))
}

tasks.compileKotlin { dependsOn(copyTask) }
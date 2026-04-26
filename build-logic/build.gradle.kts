import com.makeboon.gradle.extension.implementationDefaultVersionCatalogLibraries

plugins { `kotlin-dsl` }

kotlin {
    jvmToolchain(buildLogic.versions.kotlin.jvmToolchain.get().toInt())
}

dependencies {
    implementation(buildLogic.gradlePlugin.gradlePublish)
    implementation(buildLogic.gradlePlugin.kotlin)
    implementation(buildLogic.gradlePlugin.binaryCompatibility)
    implementation(buildLogic.gradlePlugin.serialization)
    implementation(buildLogic.gradlePlugin.buildconfig)
    implementation(buildLogic.gradlePlugin.dokka)
    implementation(buildLogic.gradlePlugin.publish)

    implementationDefaultVersionCatalogLibraries()
}

private val copyTask by tasks.registering(Copy::class) {
    val packagePath = "src/main/kotlin/com/makeboon/gradle"
    val srcDir = projectDir.resolve(packagePath)
    val destDir = rootDir.resolve("../convention/$packagePath")

    into(destDir)
    from(srcDir) {
        include(
            *listOf(
                "artifact",
                "dokka",
                "publish",
                "kotlin-jvm"
            ).map { "$it.gradle.kts" }.toTypedArray()
        )
    }
}

tasks.compileKotlin { dependsOn(copyTask) }

import com.makeboon.gradle.extension.implementationDefaultVersionCatalogLibraries

plugins { `kotlin-dsl` }

kotlin {
    jvmToolchain(buildLogic.versions.kotlin.jvmToolchain.get().toInt())
}

dependencies {
    implementation(buildLogic.gradlePlugin.kotlin)
    implementation(buildLogic.gradlePlugin.dokka)
    implementation(buildLogic.gradlePlugin.publish)
    implementation(buildLogic.gradlePlugin.gradlePublish)

    implementationDefaultVersionCatalogLibraries()
}

private val copyTask by tasks.registering(Copy::class) {
    val packagePath = "src/main/kotlin/com/makeboon/gradle"
    from(projectDir.resolve(packagePath))
    into(rootDir.resolve("../convention/$packagePath"))
    include(*listOf("artifact", "dokka").map { "$it.gradle.kts" }.toTypedArray())
}

tasks.compileKotlin { dependsOn(copyTask) }

plugins {
    `kotlin-dsl`
}

kotlin {
    jvmToolchain(core.versions.kotlin.jvmToolchain.get().toInt())
}

dependencies {
    implementation(core.gradlePlugin.kotlin)
}

gradlePlugin {
    plugins {
        create("jvmToolchain") {
            id = "convention.jvmToolchain"
            implementationClass = "gradle.JVMToolchainPlugin"
        }
        create("publish") {
            id = "convention.publish"
            implementationClass = "gradle.PublishPlugin"
        }
    }
}

with(tasks) {
    val taskName = "copyConventionDirs"
    register(taskName) {
        doLast {
            val targetPath = "src/main/kotlin/com/makeboon/gradle"
            val srcDir = file(targetPath)
            if (!srcDir.exists()) throw GradleException("$srcDir not found")
            val dstDir = layout.projectDirectory.file("../convention/$targetPath").asFile

            copy {
                dstDir.delete()
                from(srcDir)
                into(dstDir)
            }
        }
    }
    named("jar") { finalizedBy(taskName) }
}
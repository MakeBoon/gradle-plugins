plugins {
    `kotlin-dsl`
    alias(libs.plugins.convention.jvmToolchain)
    alias(libs.plugins.convention.publish)
}

dependencies {
}

gradlePlugin {
    plugins {
        create("dependencyResolutionManagement") {
            id = "convention.dependencyResolutionManagement"
            implementationClass = "gradle.DependencyResolutionManagementPlugin"
        }
    }
}
plugins {
    `kotlin-dsl`
    alias(core.plugins.convention.jvmToolchain)
    alias(core.plugins.convention.publish)
}

dependencies {
}

gradlePlugin {
    plugins {
        create("dependencyResolutionManagement") {
            id = "convention.dependencyResolutionManagement"
            implementationClass = "gradle.DependencyResolutionManagementPlugin"
        }
        create("dependencyResolutionManagementForBuildSrc") {
            id = "convention.dependencyResolutionManagementForBuildSrc"
            implementationClass = "gradle.DependencyResolutionManagementForBuildSrcPlugin"
        }
    }
}
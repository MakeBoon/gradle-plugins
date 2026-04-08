dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    versionCatalogs {
        listOf(
            "core" to "build-plugins",
        ).forEach { (name, target) ->
            create(name) {
                from(files("../../gradle-resources/versions/$target.toml"))
            }
        }
    }
}

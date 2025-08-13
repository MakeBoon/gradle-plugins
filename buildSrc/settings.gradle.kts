rootProject.name = "buildSrc"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("../resources/versions/build-plugins.toml"))
        }
    }
}

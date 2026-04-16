package com.makeboon.gradle

plugins {
    id("com.makeboon.gradle.artifact")
    id("version-catalog")
    id("com.vanniktech.maven.publish")
}

catalog {
    versionCatalog {
        from(files("${project.name}.toml"))
    }
}

mavenPublishing {
    coordinates(artifactId = "catalog-$name")
}
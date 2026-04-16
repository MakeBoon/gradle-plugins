plugins {
    id("com.makeboon.gradle.catalog-publish")
}

catalog {
    versionCatalog {
        version("gradle-kmp", "$version")
    }
}

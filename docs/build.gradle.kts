plugins {
    id("com.makeboon.gradle.dokka")
}

dependencies {
    dokka(projects.convention)
    dokka(projects.kmp)
}

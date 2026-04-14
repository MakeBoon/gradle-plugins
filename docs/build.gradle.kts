plugins {
    id("com.makeboon.dokka")
}

dependencies {
    dokka(projects.convention)
    dokka(projects.kmp)
}

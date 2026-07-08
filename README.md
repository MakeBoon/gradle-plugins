# gradle-plugins

## [Kotlin Multiplatform](kmp)

Set `makeboon.kmp.wasmJs=true` in a module's (or the root) `gradle.properties` to add a
wasmJs browser target to the `module-*` plugins. Application modules also get
`binaries.executable()`; Room3 is skipped for wasmJs modules since it publishes no wasm artifacts.

## Publishing

### Maven Local

```shell
./gradlew publishToMavenLocal
```

### Gradle Plugin Portal

The version (`VERSION_NAME`) and group (`GROUP`) are defined in the root [gradle.properties](gradle.properties).
Per-plugin displayName / description / tags are filled in by
[gradle-publish.gradle.kts](build-logic/src/main/kotlin/com/makeboon/gradle/gradle-publish.gradle.kts).

Version catalogs (`convention/src/main/resources/com/makeboon/gradle/catalogs/*.toml`)
are not deployed separately; they are bundled into the `:convention` jar as resources
and registered when the `com.makeboon.gradle.settings` plugin is applied:

```kotlin
// consumer settings.gradle.kts
plugins {
    id("com.makeboon.gradle.settings") version "<version>"
}
```

Publishing runs via the [publish.yml](.github/workflows/publish.yml) workflow when a
GitHub Release is published (or triggered manually from Actions), executing `publishPlugins`.
The following GitHub Secrets must be configured beforehand:

| Secret | Value |
|---|---|
| `GRADLE_PUBLISH_KEY` | API key of your [Plugin Portal](https://plugins.gradle.org) account |
| `GRADLE_PUBLISH_SECRET` | Plugin Portal API secret |

> The first publication goes through the Portal's manual approval process.
> During review you may be asked to prove ownership of the `com.makeboon` namespace
> (the makeboon.com domain or the GitHub org).

To publish directly from a local machine:

```shell
./gradlew publishPlugins -Pgradle.publish.key=... -Pgradle.publish.secret=...
```

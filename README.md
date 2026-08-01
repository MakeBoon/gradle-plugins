# gradle-plugins

## [Kotlin Multiplatform](kmp)

Module plugins are picked by target platform rather than an opt-in flag — each id is
`com.makeboon.gradle.kmp.<plugin>`:

| Plugin | Targets | library | compose |
|---|---|---|---|
| `module-mobile-application` | Android, Apple | ✗ | ✓ |
| `module-mobile-application-library` | Android, Apple | ✓ | ✗ |
| `module-mobile-application-compose-library` | Android, Apple | ✓ | ✓ |
| `module-mobile-library` | Android, Apple | ✓ | ✗ |
| `module-mobile-compose-library` | Android, Apple | ✓ | ✓ |
| `module-web-application` | wasmJs | ✗ | ✓ |
| `module-web-application-library` | wasmJs | ✓ | ✗ |
| `module-web-application-compose-library` | wasmJs | ✓ | ✓ |
| `module-web-library` | wasmJs | ✓ | ✗ |
| `module-web-compose-library` | wasmJs | ✓ | ✓ |
| `module-android-application` | Android only (not KMP) | — | ✓ |

`application` plugins add `binaries.executable()` for their web/native binaries; `library`
plugins publish instead. Room3 and Metro are applied to every mobile/web module. Room3 wires
`androidx.sqlite:sqlite-web` (WebWorkerSQLiteDriver) on `wasmJsMain` and the bundled SQLite
driver on the JVM/Android/native source sets, based on whichever targets the module actually
configures (see `room3.gradle.kts`).

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

# gradle-plugins

## [Kotlin Multiplatform](kmp)

## Publishing

### Maven Local

```shell
./gradlew publishToMavenLocal
```

### Gradle Plugin Portal

버전(`VERSION_NAME`)과 그룹(`GROUP`)은 루트 [gradle.properties](gradle.properties),
플러그인별 displayName / description / tags 는 [gradle-publish.gradle.kts](build-logic/src/main/kotlin/com/makeboon/gradle/gradle-publish.gradle.kts) 에서 채워진다.

버전 카탈로그(`catalog/*/*.toml`)는 별도 배포 없이 `:convention` jar 에 리소스로 내장되고,
`com.makeboon.gradle.settings` 플러그인이 적용될 때 등록된다:

```kotlin
// consumer settings.gradle.kts
plugins {
    id("com.makeboon.gradle.settings") version "<version>"
}
```

GitHub Release 발행(또는 Actions 수동 실행) 시 [publish.yml](.github/workflows/publish.yml) 워크플로가 `publishPlugins` 를 실행한다.
사전에 GitHub Secrets 등록이 필요하다:

| Secret | 값 |
|---|---|
| `GRADLE_PUBLISH_KEY` | [Plugin Portal](https://plugins.gradle.org) 계정의 API key |
| `GRADLE_PUBLISH_SECRET` | Plugin Portal API secret |

> 최초 배포는 Portal 측 수동 승인을 거친다. 승인 과정에서 `com.makeboon` namespace
> 소유 확인(makeboon.com 도메인 또는 GitHub org 증빙)을 요구할 수 있다.

로컬에서 직접 배포하려면:

```shell
./gradlew publishPlugins -Pgradle.publish.key=... -Pgradle.publish.secret=...
```

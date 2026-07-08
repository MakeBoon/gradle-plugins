# gradle-plugins

## [Kotlin Multiplatform](kmp)

## Publishing

### Maven Local

```shell
./gradlew publishToMavenLocal
```

### Gradle Plugin Portal (플러그인: `:convention`, `:kmp`)

버전(`VERSION_NAME`)과 그룹(`GROUP`)은 루트 [gradle.properties](gradle.properties),
플러그인별 displayName / description / tags 는 [gradle-publish.gradle.kts](build-logic/src/main/kotlin/com/makeboon/gradle/gradle-publish.gradle.kts) 에서 채워진다.

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

### Maven Central (버전 카탈로그: `:catalog:*`)

Plugin Portal 은 플러그인만 호스팅하므로 버전 카탈로그는 Maven Central 로 배포한다.
필요할 때 [publish-catalogs.yml](.github/workflows/publish-catalogs.yml) 워크플로를 수동 실행한다.

| Secret | 값 |
|---|---|
| `MAVEN_CENTRAL_USERNAME` | [Central Portal](https://central.sonatype.com) user token username |
| `MAVEN_CENTRAL_PASSWORD` | Central Portal user token password |
| `SIGNING_IN_MEMORY_KEY` | GPG secret key (ASCII-armored, `gpg --export-secret-keys --armor <keyId>` 결과에서 헤더/푸터 제외) |
| `SIGNING_IN_MEMORY_KEY_PASSWORD` | GPG key passphrase |

> Central Portal 에서 `com.makeboon` namespace 인증이 되어 있어야 하며,
> 업로드된 deployment 는 Portal UI 에서 수동 release 한다.
> 카탈로그를 원격 배포할 필요가 없다면 이 워크플로는 지워도 된다.

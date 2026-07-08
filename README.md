# gradle-plugins

## [Kotlin Multiplatform](kmp)

## Publishing

### Maven Local

```shell
./gradlew publishToMavenLocal
```

### Maven Central (Central Portal)

버전(`VERSION_NAME`)과 그룹(`GROUP`), 공통 POM 메타데이터는 루트 [gradle.properties](gradle.properties),
모듈별 `POM_NAME` / `POM_DESCRIPTION` 은 각 모듈의 `gradle.properties` 에 정의되어 있다.

배포는 GitHub Release 발행(또는 Actions 수동 실행) 시 [publish.yml](.github/workflows/publish.yml) 워크플로가 수행한다.
사전에 GitHub Secrets 등록이 필요하다:

| Secret | 값 |
|---|---|
| `MAVEN_CENTRAL_USERNAME` | [Central Portal](https://central.sonatype.com) user token username |
| `MAVEN_CENTRAL_PASSWORD` | Central Portal user token password |
| `SIGNING_IN_MEMORY_KEY` | GPG secret key (ASCII-armored, `gpg --export-secret-keys --armor <keyId>` 결과에서 헤더/푸터 제외) |
| `SIGNING_IN_MEMORY_KEY_PASSWORD` | GPG key passphrase |

> Central Portal 에서 `com.makeboon` namespace 인증이 되어 있어야 한다.

업로드된 deployment 는 Portal UI 에서 수동 release 한다.
자동 release 를 원하면 `gradle.properties` 의 `mavenCentralAutomaticPublishing=true` 를 활성화한다.

로컬에서 직접 배포하려면:

```shell
./gradlew publishToMavenCentral --no-configuration-cache \
  -PsignAllPublications=true \
  -PmavenCentralUsername=... -PmavenCentralPassword=... \
  -PsigningInMemoryKey=... -PsigningInMemoryKeyPassword=...
```

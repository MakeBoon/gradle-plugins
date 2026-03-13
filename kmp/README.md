# Gradle
- Apply the Gradle plugin in the root build script.
```
plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.wire) apply false
    alias(libs.plugins.room3) apply false
    alias(libs.plugins.gms.googleServices) apply false
    alias(libs.plugins.firebase.appDistribution) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
}
```

# Framework

### Version

- kotlin
- kotlin-jvmToolchain

### Plugin

- kotlin-multiplatform
- kotlin-serialization

# Compose

### Version

- compose

### Plugin

- compose
- compose-compiler

# KSP

### Version

- ksp

### Plugin

- ksp

# Wire

### Version

- wire

### Plugin

- wire

# Room

### Version

- ksp
- room3
- sqlite

### Plugin

- ksp
- room3

### Library

- room3
- room3-compiler
- sqlite

# Firebase

### Version

- firebase-bom

### Plugin

- gms-googleServices
- firebase-appDistribution
- firebase-crashlytics

### Library

- firebase-bom

# Android Application

### Version

- android-compileSdk
- android-compileSdkApi
- android-compileSdkExt
- android-minSdk
- android-targetSdk

### Plugin

- android-application
- kotlin-parcelize

### Resources Directory

- keystore
    - android.properties
        - StoreFile
        - StorePassword
        - KeyAlias
        - KeyPassword
- proguard

# Android Library

### Version

- android-compileSdk
- android-compileSdkApi
- android-compileSdkExt
- android-minSdk

### Plugin

- android-library
- kotlin-parcelize

# iOS Library

### Target

- iosArm64
- iosSimulatorArm64

### nativeInterop/cinterop

- NSKeyValueObserving

# ComposeFlattenDrawableResource

### Dependency

- android-tools:sdk-common
    - [maven-google](https://maven.google.com/web/index.html?q=sdk-common#com.android.tools:sdk-common)
    - [mvn](https://mvnrepository.com/artifact/com.android.tools/sdk-common)

### Svg2Vector

- [source](https://android.googlesource.com/platform/tools/base/+/refs/heads/mirror-goog-studio-main/sdk-common/src/main/java/com/android/ide/common/vectordrawable/Svg2Vector.java)
- [test](https://android.googlesource.com/platform/tools/base/+/refs/heads/mirror-goog-studio-main/sdk-common/src/test/java/com/android/ide/common/vectordrawable/VectorDrawableGeneratorTest.java)



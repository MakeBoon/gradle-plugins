package com.makeboon.gradle.kmp.extension

import com.android.build.api.dsl.CompileSdkSpec
import com.android.build.api.dsl.CompileSdkVersion
import com.android.build.api.dsl.MinSdkSpec
import com.android.build.api.dsl.MinSdkVersion
import org.gradle.api.provider.Provider

public fun CompileSdkSpec.release(
    versionProvider: Provider<String>,
    minorApiLevelProvider: Provider<String>,
    sdkExtensionProvider: Provider<String>,
): CompileSdkVersion = release(versionProvider.get().toInt()) {
    minorApiLevel = minorApiLevelProvider.get().toIntOrNull()
    sdkExtension = sdkExtensionProvider.get().toIntOrNull()
}

public fun MinSdkSpec.release(
    versionProvider: Provider<String>,
): MinSdkVersion = release(versionProvider.get().toInt())


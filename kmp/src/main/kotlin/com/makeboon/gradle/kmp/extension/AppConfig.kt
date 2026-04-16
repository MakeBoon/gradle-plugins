package com.makeboon.gradle.kmp.extension

import com.makeboon.gradle.extension.fileInRootDir
import com.makeboon.gradle.extension.getPropertyBooleanOrNull
import com.makeboon.gradle.extension.getPropertyInt
import com.makeboon.gradle.extension.toProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.typeOf

public data class AppConfig(
    val projectNamespace: String,
    val versionMajor: Int,
    val versionMinor: Int,
    val versionPatch: Int,
    val versionBuild: Int,
    val paymentTest: Boolean,
    val keyStoreDir: String,
    val proguardDir: String,
) {
    init {
        fun require(version: Int, name: String) =
            require(version in 0..99) { "$name version ($version) must be between 0 and 99." }
        require(versionMajor, "Major")
        require(versionMinor, "Minor")
        require(versionPatch, "Patch")
        require(versionBuild, "Build")

        require(versionMajor > 0 || versionMinor > 0 || versionPatch > 0) {
            "Invalid version: Major, Minor, or Patch must be at least 1."
        }
    }

    val versionCode: Int get() = versionMajor * 1_000_000 + versionMinor * 10_000 + versionPatch * 100 + versionBuild
    val versionName: String get() = "$versionMajor.$versionMinor.$versionPatch"
    val versionNameWithBuild: String get() = "$versionName.$versionBuild"

    public companion object {
        public fun configure(project: Project): AppConfig = with(project) {
            with(fileInRootDir("AppConfig.xcconfig").toProperties()) {
                AppConfig(
                    projectNamespace = getProperty("ProjectNamespace"),
                    versionMajor = getPropertyInt("VersionMajor"),
                    versionMinor = getPropertyInt("VersionMinor"),
                    versionPatch = getPropertyInt("VersionPatch"),
                    versionBuild = getPropertyInt("VersionBuild"),
                    paymentTest = getPropertyBooleanOrNull("PaymentTest") ?: false,
                    keyStoreDir = getProperty("KeyStoreDir", "../kmp-resources/android"),
                    proguardDir = getProperty("ProguardDir", "../kmp-resources/proguard"),
                ).apply {
                    extensions.add(typeOf(), "appConfig", this)
                    group = projectNamespace
                    version = versionNameWithBuild
                }
            }
        }
    }
}
package com.makeboon.kmp.gradle

import com.makeboon.gradle.*
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

class FrameworkPlugin(private val library: Boolean) : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(core.pluginId("kotlin-multiplatform"))
            apply(core.pluginId("kotlin-serialization"))
        }

        extensions.configure<KotlinMultiplatformExtension> {
            jvmToolchain(core.versionInt("kotlin-jvmToolchain"))
            if (library) explicitApi()
            compilerOptions {
                val kotlinVersion = KotlinVersion.fromVersion(core.version("kotlin-compile"))
                languageVersion.set(kotlinVersion)
                apiVersion.set(kotlinVersion)
                progressiveMode.set(true)
                freeCompilerArgs.addAll(
                    `-X`(
                        "expect-actual-classes", // https://kotlinlang.org/docs/multiplatform/multiplatform-expect-actual.html#expected-and-actual-classes
                        // whatsnew22
                        "context-parameters", // https://kotlinlang.org/docs/whatsnew22.html#preview-of-context-parameters
                        /*
                         * https://kotlinlang.org/docs/whatsnew22.html#preview-of-context-sensitive-resolution
                         * https://kotlinlang.org/docs/whatsnew-eap.html#changes-to-context-sensitive-resolution
                         */
                        "context-sensitive-resolution",
                        // whatsnew2220
                        "allow-reified-type-in-catch", // https://kotlinlang.org/docs/whatsnew2220.html#support-for-reified-types-in-catch-clauses
                        "when-expressions=indy", // https://kotlinlang.org/docs/whatsnew2220.html#kotlin-jvm-support-invokedynamic-with-when-expressions
                        // whatsnew23
                        "return-value-checker=check", // https://kotlinlang.org/docs/whatsnew23.html#unused-return-value-checker
                        "explicit-backing-fields", // https://kotlinlang.org/docs/whatsnew23.html#explicit-backing-fields
                        // whatsnew-eap: ?
                    )
                )
            }
        }
    }
}
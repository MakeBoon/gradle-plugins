package com.makeboon.gradle.kmp

import com.makeboon.gradle.extension.`-X`
import com.makeboon.gradle.extension.apply
import com.makeboon.gradle.extension.buildLogic
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

internal class FrameworkPlugin(val library: Boolean) : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(buildLogic.plugins.kotlin.multiplatform)
            apply(buildLogic.plugins.kotlin.serialization)
        }

        extensions.configure<KotlinMultiplatformExtension> {
            jvmToolchain(buildLogic.versions.kotlin.jvmToolchain.get().toInt())
            if (library) explicitApi()
            @OptIn(ExperimentalAbiValidation::class)
            abiValidation { enabled = true }
            compilerOptions {
                val kotlinVersion = KotlinVersion.fromVersion(buildLogic.versions.kotlin.compile.get())
                languageVersion = kotlinVersion
                apiVersion = kotlinVersion
                progressiveMode = true
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
                        // whatsnew2320
                        "name-based-destructuring=name-mismatch", // https://kotlinlang.org/docs/whatsnew2320.html#language-name-based-destructuring
                        // whatsnew-eap: 2.4.0-Beta1
                        "explicit-context-arguments", // https://kotlinlang.org/docs/whatsnew-eap.html#explicit-context-arguments-for-context-parameters
                        "klib-ir-inliner=full", // https://kotlinlang.org/docs/whatsnew-eap.html#consistent-intra-module-function-inlining-during-klib-compilation
                        // whatsnew-eap: ?
                    )
                )
            }
        }
    }
}
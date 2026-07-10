package com.makeboon.gradle.kmp.plugin

import com.makeboon.gradle.extension.`-X`
import com.makeboon.gradle.extension.apply
import com.makeboon.gradle.extension.buildLogic
import com.makeboon.gradle.extension.kotlinx
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
            abiValidation()
            compilerOptions {
                val kotlinVersion = KotlinVersion.fromVersion(buildLogic.versions.kotlin.compile.get())
                languageVersion = kotlinVersion
                apiVersion = kotlinVersion
                progressiveMode = true
                freeCompilerArgs.addAll(
                    `-X`(
                        "expect-actual-classes", // https://kotlinlang.org/docs/multiplatform/multiplatform-expect-actual.html#expected-and-actual-classes
                        // whatsnew22
                        "context-sensitive-resolution", // https://kotlinlang.org/docs/whatsnew22.html#preview-of-context-sensitive-resolution
                        // whatsnew23
                        "return-value-checker=check", // https://kotlinlang.org/docs/whatsnew23.html#unused-return-value-checker
                        // whatsnew2320
                        "name-based-destructuring=name-mismatch", // https://kotlinlang.org/docs/whatsnew2320.html#language-name-based-destructuring
                        // whatsnew24
                        "explicit-context-arguments", // https://kotlinlang.org/docs/whatsnew24.html#explicit-context-arguments-for-context-parameters
                        "collection-literals", // https://kotlinlang.org/docs/whatsnew24.html#support-for-collection-literals
                        "allow-returns-result-of", // https://kotlinlang.org/docs/whatsnew24.html#improved-unused-result-checks-for-higher-order-functions
                        // whatsnew-eap: 2.4.20-Beta1
                        // whatsnew-eap: ?
                    )
                )
            }

            applyDefaultHierarchyTemplate()

            sourceSets.commonTest.dependencies {
                implementation(kotlin("test"))
                implementation(kotlinx.kotlinx.coroutines.test)
            }
        }
    }
}
package com.makeboon.gradle.kmp

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * https://kotlinlang.org/docs/wasm-get-started.html
 *
 * Adds a wasmJs browser target; application modules also get an executable
 * binary so the bundle can be served as a web app (e.g. Cloudflare Pages).
 */
public class WasmJsTargetPlugin(
    private val application: Boolean,
) : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        extensions.configure<KotlinMultiplatformExtension> {
            @OptIn(ExperimentalWasmDsl::class)
            wasmJs {
                browser()
                if (application) binaries.executable()
            }
        }
    }
}

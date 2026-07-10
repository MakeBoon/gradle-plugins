package com.makeboon.gradle.kmp.target

import com.makeboon.gradle.extension.kotlinx
import com.makeboon.gradle.extension.toCamelCase
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

public class WebTargetPlugin(private val library: Boolean) : TargetPlugin() {
    override fun apply(target: Project): Unit = with(target) {
        extensions.configure<KotlinMultiplatformExtension> {
            @OptIn(ExperimentalWasmDsl::class)
            wasmJs {
                val outputName = target.name.toCamelCase()
                binaries.executable()
                outputModuleName.set(outputName)
                browser {
                    commonWebpackConfig {
                        outputFileName = "$outputName.js"
                    }
                    testTask {
                        useKarma {
                            useChromeHeadless()
                        }
                    }
                }
            }

            sourceSets.webMain.dependencies {
                implementation(kotlinx.kotlinx.browser)
            }
        }
    }
}
package pro.crestfi.kmp.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import pro.crestfi.gradle.`-X`
import pro.crestfi.gradle.core
import pro.crestfi.gradle.pluginId
import pro.crestfi.gradle.version
import pro.crestfi.gradle.versionInt

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
                        "data-flow-based-exhaustiveness", // https://kotlinlang.org/docs/whatsnew2220.html#data-flow-based-exhaustiveness-checks-for-when-expressions
                        "allow-reified-type-in-catch", // https://kotlinlang.org/docs/whatsnew2220.html#support-for-reified-types-in-catch-clauses
                        "when-expressions=indy", // https://kotlinlang.org/docs/whatsnew2220.html#kotlin-jvm-support-invokedynamic-with-when-expressions
                        // whatsnew-eap
                        "return-value-checker=check", // https://kotlinlang.org/docs/whatsnew-eap.html#unused-return-value-checker
                    )
                )
            }
        }
    }
}
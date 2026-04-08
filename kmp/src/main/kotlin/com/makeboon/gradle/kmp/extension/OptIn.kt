package com.makeboon.gradle.kmp.extension

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import com.makeboon.gradle.extension.`-opt-in`
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

public enum class OptIn(private vararg val value: String) {
    Shared(
        "kotlin.concurrent.atomics.ExperimentalAtomicApi",
        "kotlin.experimental.ExperimentalNativeApi",
        "kotlin.experimental.ExperimentalObjCName",
        "kotlin.time.ExperimentalTime",
        "kotlin.uuid.ExperimentalUuidApi",
        "kotlinx.cinterop.ExperimentalForeignApi",
        "kotlinx.coroutines.ExperimentalCoroutinesApi",
        "kotlinx.coroutines.ExperimentalForInheritanceCoroutinesApi",
    ),
    Compose(
        "org.jetbrains.compose.resources.InternalResourceApi",
        "org.jetbrains.compose.resources.ExperimentalResourceApi",
        "androidx.compose.material3.ExperimentalMaterial3Api",
        "androidx.compose.material3.ExperimentalMaterial3ExpressiveApi",
        "androidx.compose.foundation.ExperimentalFoundationApi",
        "androidx.compose.foundation.layout.ExperimentalLayoutApi",
        "androidx.compose.ui.ExperimentalComposeUiApi",
        //
        "dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi",
    ),
    ;

    public val values: List<String> get() = value.toList()

    public companion object {
        public fun configure(project: Project, library: Boolean, compose: Boolean): Unit = with(project) {
            val optIns = mutableListOf<OptIn>().apply {
                add(Shared)
                if (compose) add(Compose)
            }.flatMap { it.values }

            extensions.configure<KotlinMultiplatformExtension> {
                compilerOptions.freeCompilerArgs.addAll(
                    `-opt-in`(*optIns.toTypedArray())
                )

                if (library) {
                    targets.withType<KotlinMultiplatformAndroidLibraryTarget>().configureEach {
                        compilerOptions.freeCompilerArgs.addAll(
                            `-opt-in`(
                                "androidx.media3.common.util.UnstableApi"
                            )
                        )
                    }
                }
            }
        }
    }
}

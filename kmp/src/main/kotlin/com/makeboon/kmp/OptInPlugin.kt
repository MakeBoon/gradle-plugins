package com.makeboon.kmp

import com.android.build.api.dsl.androidLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import com.makeboon.gradle.`-opt-in`

class OptInPlugin(
    private val compose: Boolean = false,
    private val library: Boolean = false,
) : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        extensions.configure<KotlinMultiplatformExtension> {
            val optIns = mutableListOf<OptIn>().apply {
                add(OptIn.Shared)
                if (compose) add(OptIn.Compose)
            }.flatMap { it.values }

            compilerOptions.freeCompilerArgs.addAll(
                `-opt-in`(*optIns.toTypedArray())
            )

            if (library) {
                androidLibrary {
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

enum class OptIn(private vararg val value: String) {
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

    val values: List<String> get() = value.toList()
}

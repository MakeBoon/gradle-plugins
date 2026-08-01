package com.makeboon.gradle.kmp

import com.makeboon.gradle.kmp.plugin.AndroidApplicationPlugin
import com.makeboon.gradle.kmp.plugin.ComposePlugin

listOf(
    AndroidApplicationPlugin,
    ComposePlugin,
).forEach { it.apply(project) }

plugins {}

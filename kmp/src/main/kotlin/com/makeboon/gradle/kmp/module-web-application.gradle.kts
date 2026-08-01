package com.makeboon.gradle.kmp

import com.makeboon.gradle.kmp.target.ModulePlugin
import com.makeboon.gradle.kmp.target.WebTargetPlugin

private val library = false

ModulePlugin.apply(
    project,
    library = library,
    compose = true,
    publish = false,
    WebTargetPlugin(library),
)

plugins {}

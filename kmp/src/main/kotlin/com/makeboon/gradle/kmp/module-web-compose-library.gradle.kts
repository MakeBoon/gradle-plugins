package com.makeboon.gradle.kmp

import com.makeboon.gradle.kmp.target.ModulePlugin
import com.makeboon.gradle.kmp.target.WebTargetPlugin

private val library = true

ModulePlugin.apply(
    project,
    library = library,
    compose = true,
    publish = true,
    WebTargetPlugin(library),
)

plugins {}

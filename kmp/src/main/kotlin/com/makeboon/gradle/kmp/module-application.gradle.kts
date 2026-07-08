package com.makeboon.gradle.kmp

import com.makeboon.gradle.kmp.target.AllTargets
import com.makeboon.gradle.kmp.target.ModulePlugin

private val library = false

ModulePlugin.apply(
    project,
    library = library,
    compose = true,
    publish = false,
    *AllTargets(library),
)

plugins {}

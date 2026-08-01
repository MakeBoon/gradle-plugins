package com.makeboon.gradle.kmp

import com.makeboon.gradle.kmp.target.AllTargets
import com.makeboon.gradle.kmp.target.ModulePlugin

private val library = true

ModulePlugin.apply(
    project,
    library = library,
    compose = true,
    publish = true,
    *AllTargets(library),
)

plugins {}

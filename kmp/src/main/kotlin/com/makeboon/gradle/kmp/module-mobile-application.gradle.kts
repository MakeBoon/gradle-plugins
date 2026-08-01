package com.makeboon.gradle.kmp

import com.makeboon.gradle.kmp.target.MobileTargets
import com.makeboon.gradle.kmp.target.ModulePlugin

ModulePlugin.apply(
    project,
    library = false,
    compose = true,
    publish = false,
    *MobileTargets,
)

plugins {}

package com.makeboon.gradle.kmp.target

import com.makeboon.gradle.kmp.extension.iosTargets
import org.gradle.api.Plugin
import org.gradle.api.Project

public abstract class TargetPlugin : Plugin<Project>

public fun AllTargets(library: Boolean): Array<TargetPlugin> = arrayOf(*MobileTargets, WebTargetPlugin(library))
public val MobileTargets: Array<TargetPlugin> = arrayOf(AndroidTargetPlugin, AppleTargetPlugin { iosTargets })
package com.makeboon.gradle.kmp

import com.makeboon.gradle.extension.apply
import com.makeboon.gradle.extension.kmpAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project

public class FirebasePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(kmpAndroid.plugins.gms.googleServices)
            apply(kmpAndroid.plugins.firebase.appDistribution)
            apply(kmpAndroid.plugins.firebase.crashlytics)
        }
    }
}
package com.makeboon.kmp.gradle

import com.makeboon.gradle.kmpAndroid
import com.makeboon.gradle.pluginId
import org.gradle.api.Plugin
import org.gradle.api.Project

class FirebasePlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(kmpAndroid.pluginId("gms-googleServices"))
            apply(kmpAndroid.pluginId("firebase-appDistribution"))
            apply(kmpAndroid.pluginId("firebase-crashlytics"))
        }
    }
}
package com.makeboon.gradle.extension

import org.gradle.api.initialization.Settings

public fun Settings.createVersionCatalogs(vararg targets: String) {
    dependencyResolutionManagement {
        targets.forEach { target ->
            versionCatalogs.create(target.toCamelCase()) {
                from("com.makeboon.gradle:catalog-$target:0.0.1")
            }
        }
    }
}

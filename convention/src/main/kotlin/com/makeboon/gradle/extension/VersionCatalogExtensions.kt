package com.makeboon.gradle.extension

import com.makeboon.gradle.GROUP_ID
import com.makeboon.gradle.VERSION
import org.gradle.api.initialization.Settings

public fun Settings.createVersionCatalogs(vararg targets: String) {
    dependencyResolutionManagement {
        targets.forEach { target ->
            versionCatalogs.create(target.toCamelCase()) {
                from("$GROUP_ID:catalog-$target:$VERSION")
            }
        }
    }
}

package com.makeboon.gradle

import com.makeboon.gradle.extension.moduleNamespaceForPackage
import com.makeboon.gradle.extension.support

plugins {
    id("app.cash.sqldelight")
}

sqldelight {
    databases {
        register("") {
//            dialect(support.sqldelight.dialect.postgresql)
            packageName.set(moduleNamespaceForPackage)
//            generateAsync.set(true)
        }
    }
}

package com.makeboon.gradle

plugins {
    id("com.squareup.wire")
}

wire {
    // https://square.github.io/wire/wire_compiler/#kotlin
    kotlin {}
}

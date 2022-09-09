buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
        classpath("org.jetbrains.kotlin:kotlin-script-runtime:1.7.10")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.42")
    }
}

plugins {
    id("com.pratclot.orderly") version "1.0.0-SNAPSHOT"
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"

    id("org.jetbrains.kotlin.jvm") version "1.7.10" apply false
    id("org.jetbrains.kotlin.android") version "1.7.10" apply false
}

allprojects {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}

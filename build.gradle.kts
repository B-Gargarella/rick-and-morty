// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.7.0" apply false
    id("com.android.library") version "8.7.0" apply false
    id("org.jetbrains.kotlin.android") version "2.0.20" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.20" apply false
    id("com.google.devtools.ksp") version "2.0.20-1.0.25" apply false
    id("com.google.dagger.hilt.android") version "2.52" apply false
}

buildscript {
    apply(from = "versions.gradle")

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        val androidVersion = rootProject.extra["android_version"]
        classpath("com.android.tools.build:gradle:$androidVersion")
        val kotlinVersion = rootProject.extra["kotlin_version"]
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        val daggerHiltVersion = rootProject.extra["dagger_hilt_version"]
        classpath("com.google.dagger:hilt-android-gradle-plugin:$daggerHiltVersion")
    }

    tasks.register("clean", Delete::class) {
        delete(rootProject.layout.buildDirectory)
    }
}
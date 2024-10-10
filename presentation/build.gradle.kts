import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

android {
    apply(from = "../versions.gradle")

    namespace = "com.bgargarella.ram.presentation"
    compileSdk = extra["compile_sdk_version"] as Int

    defaultConfig {
        minSdk = extra["min_sdk_version"] as Int

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildFeatures {
            compose = true
        }

        composeCompiler {
            featureFlags = setOf(
                ComposeFeatureFlag.StrongSkipping.disabled()
            )
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    val javaVersion = extra["java_version"] as JavaVersion

    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    kotlinOptions {
        jvmTarget = javaVersion.toString()
    }

    buildToolsVersion = extra["build_tools_version"] as String
}

dependencies {
    implementation(project(":domain"))

    val daggerHiltVersion = rootProject.extra["dagger_hilt_version"]
    implementation("com.google.dagger:hilt-android:$daggerHiltVersion")
    ksp("com.google.dagger:hilt-android-compiler:$daggerHiltVersion")

    val navigationVersion = rootProject.extra["navigation_version"]
    implementation("androidx.navigation:navigation-compose:$navigationVersion")

    val hiltNavigationVersion = rootProject.extra["hilt_navigation_version"]
    implementation("androidx.hilt:hilt-navigation-compose:$hiltNavigationVersion")

    val pagingVersion = rootProject.extra["paging_version"]
    implementation("androidx.paging:paging-compose:$pagingVersion")

    val composeVersion = rootProject.extra["compose_version"]
    implementation("androidx.compose.ui:ui:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")

    val material3Version = rootProject.extra["material3_version"]
    implementation("androidx.compose.material3:material3:$material3Version")

    val coilVersion = rootProject.extra["coil_version"]
    implementation("io.coil-kt:coil-compose:$coilVersion")
}
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
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

        composeOptions {
            kotlinCompilerExtensionVersion = extra["compose_version"] as String
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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

    val accompanistSystemUiControllerVersion = rootProject.extra["accompanist_systemuicontroller_version"]
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanistSystemUiControllerVersion")
}
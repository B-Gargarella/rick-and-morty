plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
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
            buildConfig = true
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
    ksp("com.google.dagger:hilt-compiler:$daggerHiltVersion")
    // ksp("com.google.dagger:hilt-android-compiler:$daggerHiltVersion")

    val navigationVersion = rootProject.extra["navigation_version"]
    implementation("androidx.navigation:navigation-compose:$navigationVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    implementation("androidx.paging:paging-compose:${rootProject.extra["paging_version"]}")

    val composeVersion = rootProject.extra["compose_version"]

    implementation("androidx.compose.ui:ui:$composeVersion")
    // implementation("androidx.compose.ui:ui-graphics:$composeVersion")
    // implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.compose.material3:material3:1.1.2")

    // TODO("SACAR ESTO")
    // debugImplementation("androidx.customview:customview:1.2.0-alpha02")
    // debugImplementation("androidx.customview:customview-poolingcontainer:1.0.0")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")

    implementation("io.coil-kt:coil-compose:2.5.0")

    testImplementation("junit:junit:${rootProject.extra["junit_version"]}")
    androidTestImplementation("androidx.test.ext:junit:${rootProject.extra["ext_junit_version"]}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${rootProject.extra["espresso_core_version"]}")
}
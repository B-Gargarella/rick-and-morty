plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

android {
    apply(from = "../versions.gradle")

    namespace = "com.bgargarella.ram"
    compileSdk = extra["compile_sdk_version"] as Int

    defaultConfig {
        applicationId = "com.bgargarella.ram"
        minSdk = extra["min_sdk_version"] as Int
        targetSdk = extra["target_sdk_version"] as Int
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildFeatures {
            buildConfig = true
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = extra["compose_version"] as String
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildToolsVersion = extra["build_tools_version"] as String
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":presentation"))

    val daggerHiltVersion = rootProject.extra["dagger_hilt_version"]
    implementation("com.google.dagger:hilt-android:$daggerHiltVersion")
    ksp("com.google.dagger:hilt-compiler:$daggerHiltVersion")
    // ksp("com.google.dagger:hilt-android-compiler:$daggerHiltVersion")

    // val navigationVersion = rootProject.extra["navigation_version"]
    // implementation("androidx.navigation:navigation-compose:$navigationVersion")
    // implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    implementation("androidx.activity:activity-compose:1.8.0")

    val retrofitVersion = rootProject.extra["retrofit_version"]
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")

    val okhttp3Version = rootProject.extra["okhttp3_version"]
    // implementation("com.squareup.okhttp3:okhttp:$okhttp3Version")
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttp3Version")

    implementation("com.squareup.moshi:moshi-kotlin:${rootProject.extra["moshi_kotlin_version"]}")

    val roomVersion = rootProject.extra["room_version"]
    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:$roomVersion")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")
    // implementation("androidx.room:room-paging:$roomVersion")

    testImplementation("junit:junit:${rootProject.extra["junit_version"]}")
    androidTestImplementation("androidx.test.ext:junit:${rootProject.extra["ext_junit_version"]}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${rootProject.extra["espresso_core_version"]}")
}
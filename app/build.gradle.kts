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
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":presentation"))

    val daggerHiltVersion = rootProject.extra["dagger_hilt_version"]
    implementation("com.google.dagger:hilt-android:$daggerHiltVersion")
    ksp("com.google.dagger:hilt-android-compiler:$daggerHiltVersion")

    val retrofitVersion = rootProject.extra["retrofit_version"]
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")

    val okhttp3Version = rootProject.extra["okhttp3_version"]
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttp3Version")

    val moshiKotlinVersion = rootProject.extra["moshi_kotlin_version"]
    implementation("com.squareup.moshi:moshi-kotlin:$moshiKotlinVersion")

    val roomVersion = rootProject.extra["room_version"]
    implementation("androidx.room:room-ktx:$roomVersion")

    val junitVersion = rootProject.extra["junit_version"]
    testImplementation("junit:junit:$junitVersion")
    val extJunitVersion = rootProject.extra["ext_junit_version"]
    androidTestImplementation("androidx.test.ext:junit:$extJunitVersion")
    val espressoCoreVersion = rootProject.extra["espresso_core_version"]
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoCoreVersion")
    val kotlinxCoroutinesTestVersion = rootProject.extra["kotlinx_coroutines_test_version"]
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinxCoroutinesTestVersion")
}
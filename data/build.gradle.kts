plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

android {
    apply(from = "../versions.gradle")

    namespace = "com.bgargarella.ram.data"
    compileSdk = extra["compile_sdk_version"] as Int

    defaultConfig {
        minSdk = extra["min_sdk_version"] as Int

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildFeatures {
            buildConfig = true
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

    ksp {
        arg(k = "room.schemaLocation", v = "$projectDir/schemas")
        arg(k = "room.incremental", v = "true")
    }
}

dependencies {
    implementation(project(":domain"))

    // TODO("SACAR ESTO")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")

    val daggerHiltVersion = rootProject.extra["dagger_hilt_version"]
    implementation("com.google.dagger:hilt-android:$daggerHiltVersion")
    ksp("com.google.dagger:hilt-compiler:$daggerHiltVersion")

    /*
    val okhttp3Version = rootProject.extra["okhttp3_version"]
    implementation("com.squareup.okhttp3:okhttp:$okhttp3Version")
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttp3Version")
    */

    val retrofitVersion = rootProject.extra["retrofit_version"]
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    // implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")

    /*
    val okhttp3Version = rootProject.extra["okhttp3_version"]
    implementation("com.squareup.okhttp3:okhttp:$okhttp3Version")
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttp3Version")
    */

    implementation("com.squareup.moshi:moshi-kotlin:${rootProject.extra["moshi_kotlin_version"]}")

    val roomVersion = rootProject.extra["room_version"]
    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:$roomVersion")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("androidx.room:room-paging:$roomVersion")

    testImplementation("junit:junit:${rootProject.extra["junit_version"]}")
    androidTestImplementation("androidx.test.ext:junit:${rootProject.extra["ext_junit_version"]}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${rootProject.extra["espresso_core_version"]}")
}
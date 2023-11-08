plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

android {
    apply(from = "../versions.gradle")

    namespace = "com.bgargarella.ram.domain"
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
}

dependencies {
    // TODO("SACAR ESTO")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")

    val daggerHiltVersion = rootProject.extra["dagger_hilt_version"]
    implementation("com.google.dagger:hilt-android:$daggerHiltVersion")
    ksp("com.google.dagger:hilt-compiler:$daggerHiltVersion")

    implementation("androidx.paging:paging-compose:${rootProject.extra["paging_version"]}")

    testImplementation("junit:junit:${rootProject.extra["junit_version"]}")
    androidTestImplementation("androidx.test.ext:junit:${rootProject.extra["ext_junit_version"]}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${rootProject.extra["espresso_core_version"]}")
}
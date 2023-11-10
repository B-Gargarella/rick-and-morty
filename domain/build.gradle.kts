plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
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
            buildConfigField("String", "BASE_URL", "\"https://rickandmortyapi.com/api/\"")
            buildConfigField("Boolean", "LOG_VERBOSE", "true")
            buildConfigField("Boolean", "SET_GRAY_STATUS_BAR", "false")
        }
        release {
            isMinifyEnabled = true
            buildConfigField("String", "BASE_URL", "\"https://rickandmortyapi.com/api/\"")
            buildConfigField("Boolean", "LOG_VERBOSE", "false")
            buildConfigField("Boolean", "SET_GRAY_STATUS_BAR", "false")
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
    val pagingVersion = rootProject.extra["paging_version"]
    implementation("androidx.paging:paging-compose:$pagingVersion")

    val junitVersion = rootProject.extra["junit_version"]
    testImplementation("junit:junit:$junitVersion")
    val extJunitVersion = rootProject.extra["ext_junit_version"]
    androidTestImplementation("androidx.test.ext:junit:$extJunitVersion")
    val espressoCoreVersion = rootProject.extra["espresso_core_version"]
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoCoreVersion")
}
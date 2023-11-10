plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
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
    val androidMaterialVersion = rootProject.extra["android_material_version"]
    implementation("com.google.android.material:material:$androidMaterialVersion")

    val daggerHiltVersion = rootProject.extra["dagger_hilt_version"]
    implementation("com.google.dagger:hilt-android:$daggerHiltVersion")

    val retrofitVersion = rootProject.extra["retrofit_version"]
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")

    val moshiKotlinVersion = rootProject.extra["moshi_kotlin_version"]
    implementation("com.squareup.moshi:moshi-kotlin:$moshiKotlinVersion")

    val roomVersion = rootProject.extra["room_version"]
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("androidx.room:room-paging:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    val junitVersion = rootProject.extra["junit_version"]
    testImplementation("junit:junit:$junitVersion")
    val extJunitVersion = rootProject.extra["ext_junit_version"]
    androidTestImplementation("androidx.test.ext:junit:$extJunitVersion")
    val espressoCoreVersion = rootProject.extra["espresso_core_version"]
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoCoreVersion")
}
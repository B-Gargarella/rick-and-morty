import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "${libs.versions.packageName.get()}.domain"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = libs.versions.testInstrumentationRunner.get()
        consumerProguardFiles(libs.versions.consumerRulesPro.get())
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile(libs.versions.proguardAndroidOptimize.get()),
                libs.versions.proguardRulesPro.get()
            )
        }
    }

    val javaVersionValue = libs.versions.javaVersion.get()
    val javaVersion = JavaVersion.toVersion(javaVersionValue)

    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.fromTarget(javaVersionValue)
        }
    }
}

dependencies {
    implementation(project(":domain-entities"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Add the core coroutines library, which includes Flow
    // implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:VERSION_HERE")

    // If you are using this module in an Android project (highly recommended)
    // You should also include the android-specific library for the Main dispatcher, etc.
    // This often *transitively* includes the core library, but adding both doesn't hurt.
    // implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:VERSION_HERE")

    implementation(libs.kotlinx.coroutines.core)
}
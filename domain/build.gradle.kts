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
}
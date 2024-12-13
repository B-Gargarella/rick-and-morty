import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag

plugins {
    id(libs.plugins.com.android.library.get().pluginId)
    id(libs.plugins.org.jetbrains.kotlin.android.get().pluginId)
    id(libs.plugins.org.jetbrains.kotlin.plugin.compose.get().pluginId)
    id(libs.plugins.com.google.devtools.ksp.get().pluginId)
    id(libs.plugins.dagger.hilt.android.plugin.get().pluginId)
}

android {
    namespace = "${libs.versions.packageName.get()}.presentation"
    compileSdk = libs.versions.compile.sdk.version.get().toInt()

    defaultConfig {
        minSdk = libs.versions.min.sdk.version.get().toInt()

        testInstrumentationRunner = libs.versions.test.instrumentation.runner.get()
        consumerProguardFiles(libs.versions.consumer.rules.get())

        buildFeatures {
            compose = true
        }

        composeCompiler {
            featureFlags = setOf(
                ComposeFeatureFlag.StrongSkipping.disabled()
            )
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile(libs.versions.proguard.android.optimize.get()),
                libs.versions.proguard.rules.get()
            )
        }
    }

    val javaVersion = JavaVersion.toVersion(libs.versions.java.version.get())

    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    kotlinOptions {
        jvmTarget = javaVersion.toString()
    }

    buildToolsVersion = libs.versions.build.tools.version.get()
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.navigation)
    implementation(libs.hilt.navigation)
    implementation(libs.paging)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling)

    implementation(libs.material3)
    implementation(libs.coil)
}
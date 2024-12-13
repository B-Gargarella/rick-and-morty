plugins {
    id(libs.plugins.com.android.library.get().pluginId)
    id(libs.plugins.org.jetbrains.kotlin.android.get().pluginId)
}

android {
    namespace = "${libs.versions.packageName.get()}.domain"
    compileSdk = libs.versions.compile.sdk.version.get().toInt()

    defaultConfig {
        minSdk = libs.versions.min.sdk.version.get().toInt()

        testInstrumentationRunner = libs.versions.test.instrumentation.runner.get()
        consumerProguardFiles(libs.versions.consumer.rules.get())

        buildFeatures {
            buildConfig = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            buildConfigField("String", "BASE_URL", "\"https://rickandmortyapi.com/api/\"")
        }
        release {
            isMinifyEnabled = true
            buildConfigField("String", "BASE_URL", "\"https://rickandmortyapi.com/api/\"")
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
    implementation(libs.paging)
}
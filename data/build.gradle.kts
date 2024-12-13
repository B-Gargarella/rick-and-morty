plugins {
    id(libs.plugins.com.android.library.get().pluginId)
    id(libs.plugins.org.jetbrains.kotlin.android.get().pluginId)
    id(libs.plugins.com.google.devtools.ksp.get().pluginId)
}

android {
    namespace = "${libs.versions.packageName.get()}.data"
    compileSdk = libs.versions.compile.sdk.version.get().toInt()

    defaultConfig {
        minSdk = libs.versions.min.sdk.version.get().toInt()

        testInstrumentationRunner = libs.versions.test.instrumentation.runner.get()
        consumerProguardFiles(libs.versions.consumer.rules.get())
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

    ksp {
        arg(k = "room.schemaLocation", v = "$projectDir/schemas")
        arg(k = "room.incremental", v = "true")
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.hilt.android)
    implementation(libs.retrofit)
    implementation(libs.moshi.kotlin)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    ksp(libs.room.compiler)
}
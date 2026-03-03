plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp.kotlin)
}

kotlin {
    jvmToolchain(21)
}

android {
    namespace = "com.budoxr.ett"
    compileSdk = AndroidSdk.TARGET

    defaultConfig {
        applicationId = "com.budoxr.ett"
        minSdk = AndroidSdk.MIN
        targetSdk = AndroidSdk.TARGET
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        testOptions {
            unitTests {
                isReturnDefaultValues = true
                isIncludeAndroidResources = true
            }
            animationsDisabled = true
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            buildConfigField("boolean", "SAVE_DATA_TO_JSON", "true")
        }
        release {
            isMinifyEnabled = false
            buildConfigField("boolean", "SAVE_DATA_TO_JSON", "false")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {
    // --- UI & Jetpack Compose Stack ---
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.androidx.compose.full)

    // --- Dependency Injection (Koin) ---
    implementation(libs.bundles.koin)

    // --- Persistence (Room) ---
    implementation(libs.room.runtime)
    api(libs.room.ktx) // Permite que las corrutinas de Room se propaguen a otros módulos si fuera necesario
    ksp(libs.room.compiler.ksp)

    // --- Media & Pagination ---
    implementation(libs.bundles.coil)
    implementation(libs.bundles.paging)

    // --- Background Tasks & Utils ---
    implementation(libs.timber.log)
    implementation(libs.coroutines.android)

    // --- Unit Testing (JVM) ---
    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.mockk.test)
    testImplementation(libs.robolectric.test)
    testImplementation(libs.koin.test.junit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.paging.test)

    // --- Instrumented Testing (Android) ---
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.androidx.junit.runner)
    androidTestImplementation(libs.androidx.junit.rules)
    androidTestImplementation(libs.room.test)
    androidTestImplementation(libs.workmanager.test)

    // --- Debug Tools ---
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
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
        versionCode = 2
        versionName = "0.2"

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

    signingConfigs {
        create("release") {
            val signingCredentialsFile = project.file("signin_release.txt")

            if (!signingCredentialsFile.exists()) {
                throw GradleException(
                    "Error: El archivo de credenciales 'signin_release.txt' no se encontró en:\n" +
                            signingCredentialsFile.absolutePath + "\n" +
                            "Asegúrate de que el archivo existe y está en la ubicación correcta."
                )
            }

            val credentials = signingCredentialsFile.readLines()

            if (credentials.size < 3) {
                throw GradleException(
                    "Error: El archivo 'signin_release.txt' debe contener al menos 3 líneas " +
                            "(storePassword, keyAlias, keyPassword) en ese orden."
                )
            }

            storeFile = file("D:/mydata/working/Documents/digital_certif/upload-keystore.jks")
            storePassword = credentials[0] // storePassword
            keyAlias = credentials[1]      // keyAlias
            keyPassword = credentials[2]   // keyPassword
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            buildConfigField("boolean", "SAVE_DATA_TO_JSON", "true")
        }
        release {
            isDebuggable = false
            buildConfigField("boolean", "SAVE_DATA_TO_JSON", "false")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
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
    // --- app startup ---
    implementation(libs.appstartup)

    // --- Reflection API module ---
    implementation(platform(libs.kotlin.bom))
    implementation(libs.kotlin.reflect)

    // --- UI & Jetpack Compose Stack ---
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.androidx.compose.full)

    // --- Dependency Injection (Koin) ---
    implementation(libs.bundles.koin)

    // --- Persistence (Room, Datastore Preferences) ---
    implementation(libs.room.runtime)
    api(libs.room.ktx)
    implementation(libs.androidx.compose.material3) // Permite que las corrutinas de Room se propaguen a otros módulos si fuera necesario
    ksp(libs.room.compiler.ksp)
    implementation(libs.datastore.preferences)

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


tasks.register("pipelineRelease") {
    group = "build"
    description = "Assemble, Generates and signs the release App Bundle."
    dependsOn("assembleRelease", "bundleRelease")

    println("Bundle Path: .\\app\\build\\outputs\\bundle\\release")
}

tasks.register("generateAndSignAppBundleRelease") {
    group = "build"
    description = "Generates and signs the release App Bundle."
    dependsOn("bundleRelease")
}
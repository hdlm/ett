// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // Standard AGP application plugin
    alias(libs.plugins.android.application) apply false

    // The new Compose compiler plugin (Replaces the old 'kotlin-android' requirement here)
    alias(libs.plugins.kotlin.compose) apply false

    // KSP for Room/Koin
    alias(libs.plugins.ksp.kotlin) apply false
}
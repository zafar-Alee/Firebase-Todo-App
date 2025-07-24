// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.6.0" apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.gms.google-services") version "4.4.3" apply false
}

buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.3") // optional, safe to include if needed
    }
}

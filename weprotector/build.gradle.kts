

buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.1")
    }
}

plugins {
    id("com.android.application") version "8.4.1" apply false
    alias(libs.plugins.google.gms.google.services) apply false
}
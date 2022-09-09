plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.pratclot.orderly") version "1.0.0-SNAPSHOT"
}

orderly {
//    dependenciesApiCommonLive.set {
//        api("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.7.0")
//    }
    dependenciesApiCommonLiveStrings.set(listOf())
}

repositories {
    mavenCentral()
    google()
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.pratclot.demo"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("com.android.support:appcompat-v7:28.0.0")
    implementation("com.android.support.constraint:constraint-layout:2.0.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)

    // Connects this Android app to its Firebase project
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.campusconnect"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.campusconnect"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // Standard Android and Compose libraries
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)

    // Modern Fragment version required by biometric authentication
    // and the Activity Result notification-permission launcher
    implementation(
        "androidx.fragment:fragment-ktx:1.9.1"
    )

    // Provides additional Material icons
    implementation(
        "androidx.compose.material:material-icons-extended"
    )

    // Lifecycle and ViewModel support
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(
        "androidx.lifecycle:lifecycle-runtime-compose:2.9.4"
    )
    implementation(
        "androidx.lifecycle:lifecycle-viewmodel-compose:2.9.4"
    )

    // Saves application settings on the device
    implementation(
        "androidx.datastore:datastore-preferences:1.2.1"
    )

    // Kotlin asynchronous operations
    implementation(
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2"
    )

    // Prepared for the group's REST API
    implementation(
        "com.squareup.retrofit2:retrofit:2.11.0"
    )
    implementation(
        "com.squareup.retrofit2:converter-gson:2.11.0"
    )

    // Native fingerprint and facial authentication
    implementation(
        "androidx.biometric:biometric:1.1.0"
    )

    // Reliable offline synchronisation
    implementation(
        "androidx.work:work-runtime-ktx:2.11.2"
    )

    // Firebase controls compatible Firebase SDK versions
    implementation(
        platform(
            "com.google.firebase:firebase-bom:35.1.0"
        )
    )

    // Firebase Cloud Messaging push notifications
    implementation(
        "com.google.firebase:firebase-messaging"
    )

    // Testing libraries
    testImplementation(libs.junit)

    androidTestImplementation(
        platform(libs.androidx.compose.bom)
    )
    androidTestImplementation(
        libs.androidx.compose.ui.test.junit4
    )
    androidTestImplementation(
        libs.androidx.espresso.core
    )
    androidTestImplementation(libs.androidx.junit)

    debugImplementation(
        libs.androidx.compose.ui.test.manifest
    )
    debugImplementation(
        libs.androidx.compose.ui.tooling
    )
}
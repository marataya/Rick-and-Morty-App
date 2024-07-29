plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.example.rickandmortyapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.rickandmortyapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    kapt {
        correctErrorTypes = true
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
    buildFeatures {
        viewBinding = true
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
    //Retrofit
    implementation(libs.retrofit2.retrofit)
    //Moshi
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi)
    implementation(libs.squareup.converter.moshi) // Use the latest version available

    // ViewModel with coroutines
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // Picasso
    implementation(libs.picasso)

    //Epoxy
    implementation(libs.epoxy)
    // Add the annotation processor if you are using Epoxy's annotations (recommended)
    annotationProcessor(libs.epoxy.processor)
    //epoxy paging support
    implementation(libs.epoxy.paging)

    //Paging
    implementation(libs.androidx.paging.runtime.ktx)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
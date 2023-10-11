plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "polis.app.movies"
    compileSdk = 34

    defaultConfig {
        applicationId = "polis.app.movies"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
            buildConfigField("String", "API_KEY", "\"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1N2RhMDVmZWY5MmZhZWQwZGJhM2I2MGRkYWNmMWM2OSIsInN1YiI6IjY1MjQ3ODZhNDQ3ZjljMDBjNmJjODAxYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.kr350KVwU8WIeYZI2LwFBQ8mM8r0ODy2K3CI0EWJF1s\"")

        }
        debug {
            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
            buildConfigField("String", "API_KEY", "\"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1N2RhMDVmZWY5MmZhZWQwZGJhM2I2MGRkYWNmMWM2OSIsInN1YiI6IjY1MjQ3ODZhNDQ3ZjljMDBjNmJjODAxYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.kr350KVwU8WIeYZI2LwFBQ8mM8r0ODy2K3CI0EWJF1s\"")

        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:2.7.4")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.ui:ui-tooling")


    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // DI Hilt
    implementation("com.google.dagger:hilt-android:2.44.2")
    implementation("com.google.firebase:firebase-config-ktx:21.4.1")
    implementation("com.google.firebase:firebase-perf-ktx:20.4.1")
    implementation("com.google.firebase:firebase-appcheck-debug:17.0.1")
    kapt("com.google.dagger:hilt-android-compiler:2.44")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:4.11.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")

    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    // Paging
    implementation ("androidx.paging:paging-runtime-ktx:3.2.1")
    // Jetpack Compose integration
    implementation ("androidx.paging:paging-compose:3.2.1")

    //Coil library for loading images
    implementation("io.coil-kt:coil-compose:2.4.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
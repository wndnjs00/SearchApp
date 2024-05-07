plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.example.searchapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.searchapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // retrofit (이렇게만 추가하고싶으면 version Catalog에도 추가해줘야함)
    implementation(libs.bundles.retrofit)


      // 아니면 이렇게 써줄수도 있음
//    // viewModel 추가!!
//    implementation("androidx.activity:activity-ktx:1.8.2")
//
//    // Retrofit, Gson
//    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
//    implementation ("com.squareup.retrofit2:converter-gson:2.11.0") // Gson 컨버터 추가
//
//    // OkHttp
//    implementation ("com.squareup.okhttp3:okhttp:4.12.0")
//    implementation ("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Glid
    implementation ("com.github.bumptech.glide:glide:4.16.0")


    // Lifecycle
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.7.0")
    // Coroutine
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    // viewModels
    implementation ("androidx.activity:activity-ktx:1.9.0")
    implementation ("androidx.fragment:fragment-ktx:1.7.0")

}
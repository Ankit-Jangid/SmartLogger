plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.logger"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.converter.gson)
    implementation(libs.gson)
    implementation(libs.okhttp.logging)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler.ksp)

    implementation(libs.androidx.work.runtime.ktx)

//    implementation "androidx.core:core-ktx:1.13.1"
//    implementation "androidx.room:room-runtime:2.6.1"
//    kapt "androidx.room:room-compiler:2.6.1"
//    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3"
//    implementation "androidx.work:work-runtime-ktx:2.9.0"
//    implementation "com.squareup.okhttp3:okhttp:4.12.0"
//    implementation "com.google.code.gson:gson:2.10.1"
}
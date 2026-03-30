plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)

}

android {
    namespace = "com.paisi.yufei.yuyu"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.paisi.yufei.yuyu"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0 赛事MVP版"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.recyclerview)
    implementation(libs.viewpager2)
    implementation(libs.security.crypto)
    implementation(libs.fragment.ktx)
    implementation(libs.savedstate.ktx)
    implementation(libs.activity.ktx)
    implementation(libs.annotation)
    implementation(libs.gson)
    implementation(libs.splashscreen)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
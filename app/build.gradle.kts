plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.caoquangnhat_2123110077"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.caoquangnhat_2123110077"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    // === BẮT ĐẦU PHẦN CẦN SỬA LẠI ===

    // 1. Thêm lại Firebase BOM (Bill of Materials)
    // Nó sẽ quản lý phiên bản của tất cả các thư viện Firebase.
    implementation(platform("com.google.firebase:firebase-bom:33.1.1"))

    // 2. Khai báo thư viện Firebase Authentication (KHÔNG cần ghi phiên bản)
    // Chúng ta CHỈ cần thư viện này, không cần firestore.
    implementation("com.google.firebase:firebase-auth")

    // === KẾT THÚC PHẦN CẦN SỬA LẠI ===

    implementation("de.hdodenhof:circleimageview:3.1.0")
    // Các thư viện khác của bạn giữ nguyên
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.glide)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
}
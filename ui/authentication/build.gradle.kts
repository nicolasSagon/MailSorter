plugins {
    id("kotlin-kapt")
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.nicolas.sagon.authentication"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
    }
}

dependencies {
    implementation(platform(Dependencies.kotlinBom))
    implementation(platform(Dependencies.composeBom))

    implementation(Dependencies.coreKtx)

    implementation(Dependencies.playServiceAuth)

    compose()
    hilt()

    implementation(project(Dependencies.uiCore))
    implementation(project(Dependencies.domainNavigation))
    implementation(project(Dependencies.domainAuthentication))

    testImplementation(Dependencies.jUnit)
    androidTest()
}
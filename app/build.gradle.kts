plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.plugin.serialization")
    kotlin("kapt")
}

android {
    namespace = "com.nicolas.sagon.mailSorter"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.nicolas.sagon.mailSorter"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            resValue("string", "GOOGLE_OAUTH_CLIENT_ID", getLocalProperty("googleOAuthClientId") as String)
            resValue("string", "GOOGLE_OAUTH_CLIENT_SECRET", getLocalProperty("googleOAuthClientSecret") as String)
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(platform(Dependencies.kotlinBom))
    implementation(platform(Dependencies.composeBom))

    implementation(Dependencies.lifecycleRuntimeKtx)
    implementation(Dependencies.coreKtx)

    implementation(platform(Dependencies.firebaseBom))
    implementation(Dependencies.androidDatastore)

    compose()
    retrofit()
    hilt()

    implementation(project(Dependencies.dataAuthentication))
    implementation(project(Dependencies.domainNavigation))
    implementation(project(Dependencies.domainAuthentication))
    implementation(project(Dependencies.uiCore))
    implementation(project(Dependencies.uiAuthentication))
    implementation(project(Dependencies.uiHome))

    testImplementation(Dependencies.jUnit)
    androidTest()
}

kapt {
    correctErrorTypes = true
}
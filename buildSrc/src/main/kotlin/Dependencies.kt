import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {
    const val kotlinBom = "org.jetbrains.kotlin:kotlin-bom:${Versions.kotlinBom}"
    const val kotlinCoroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutine}"
    const val composeBom = "androidx.compose:compose-bom:${Versions.composeBom}"

    const val lifecycleRuntimeKtx =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtx}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.androidXCoreKtx}"

    const val composeUi = "androidx.compose.ui:ui"
    const val composeUiGraphics = "androidx.compose.ui:ui-graphics"
    const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
    const val composeNavigation =
        "androidx.navigation:navigation-compose:${Versions.composeNavigation}"
    const val material3 = "androidx.compose.material3:material3:${Versions.composeMaterial3}"
    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"

    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    const val hiltComposeNavigation =
        "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}"

    const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebaseBom}"

    const val androidDatastore =
        "androidx.datastore:datastore-preferences:${Versions.androidDatastore}"

    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitKotlinSerialization =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.retrofitKotlinSerialization}"
    const val kotlinSerialization =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinSerialization}"

    const val playServiceAuth = "com.google.android.gms:play-services-auth:${Versions.playServiceAuth}"
    const val googleApiClient = "com.google.api-client:google-api-client-android:${Versions.googleApiClient}"

    const val dataAuthentication = ":data:authentication"
    const val domainNavigation = ":domain:navigation"
    const val domainAuthentication = ":domain:authentication"
    const val uiAuthentication = ":ui:authentication"
    const val uiCore = ":ui:core"
    const val uiHome = ":ui:home"

    const val debugComposeUiTooling = "androidx.compose.ui:ui-tooling"
    const val debugComposeUiTestManifest = "androidx.compose.ui:ui-test-manifest"

    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val androidExtJunit = "androidx.test.ext:junit:${Versions.androidXJunitExt}"
    const val androidEspressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val androidJunitUi = "androidx.compose.ui:ui-test-junit4"

}

fun DependencyHandler.retrofit() {
    implementation(Dependencies.okHttp)
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitKotlinSerialization)
    implementation(Dependencies.kotlinSerialization)
}

fun DependencyHandler.hilt() {
    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltCompiler)
    implementation(Dependencies.hiltComposeNavigation)
}

fun DependencyHandler.compose() {
    implementation(Dependencies.composeUi)
    implementation(Dependencies.composeUiGraphics)
    implementation(Dependencies.composeNavigation)
    implementation(Dependencies.material3)
    implementation(Dependencies.activityCompose)
    debugImplementation(Dependencies.composeUiToolingPreview)
    debugImplementation(Dependencies.debugComposeUiTooling)
    debugImplementation(Dependencies.debugComposeUiTestManifest)
}

fun DependencyHandler.androidTest() {
    androidTestImplementation(Dependencies.androidExtJunit)
    androidTestImplementation(Dependencies.androidEspressoCore)
    androidTestImplementation(platform(Dependencies.composeBom))
    androidTestImplementation(Dependencies.androidJunitUi)
}

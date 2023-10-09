plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_18
    targetCompatibility = JavaVersion.VERSION_18
}

dependencies {
    implementation(Dependencies.kotlinCoroutine)
    implementation(project(Dependencies.domainAuthentication))
    implementation(project(Dependencies.domainCore))
}
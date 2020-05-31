import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}


android {
    compileSdkVersion(29)
    buildToolsVersion = "29.0.2"

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib-jdk8", KotlinCompilerVersion.VERSION))

    implementation(project(":libcommon"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0")


    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")

    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.core:core-ktx:1.2.0")

    implementation("com.squareup.okhttp3:okhttp:4.2.0")
    implementation("com.squareup.okhttp3:logging-interceptor:3.5.0")

    api("android.arch.persistence.room:runtime:1.1.1")
    api("android.arch.lifecycle:extensions:1.1.1")
    kapt("android.arch.persistence.room:compiler:1.1.1")
    kapt("android.arch.lifecycle:compiler:1.1.1")

    api("com.google.code.gson:gson:2.8.6")
}

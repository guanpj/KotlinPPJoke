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

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib-jdk8", KotlinCompilerVersion.VERSION))

    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")

    //ktx
    api("androidx.core:core-ktx:1.2.0")

    //兼容包
    api("androidx.appcompat:appcompat:1.1.0")

    //material组件
    api("com.google.android.material:material:1.1.0")

    //约束布局
    api("androidx.constraintlayout:constraintlayout:1.1.3")

    //navigation导航
    api("androidx.navigation:navigation-fragment:2.1.0")
    api("androidx.navigation:navigation-ui:2.1.0")

    //包含了 viewmodel 和 livedata
    api("androidx.lifecycle:lifecycle-extensions:2.1.0")

    //或者指明使用viewmodel
    //api "androidx.lifecycle:lifecycle-viewmodel:2.1.0"
    //或者指明使用livedata
    //api 'androidx.lifecycle:lifecycle-livedata:2.1.0'
    //api 'androidx.lifecycle:lifecycle-livedata-core:2.1.0'

    //注解使用生命周期编译器
    annotationProcessor("androidx.lifecycle:lifecycle-compiler:2.1.0")

    //paging分页组件
    api("androidx.paging:paging-runtime:2.1.0")

    //页面刷新组件
    api("com.scwang.smartrefresh:SmartRefreshLayout:1.1.0")
    api("com.scwang.smartrefresh:SmartRefreshHeader:1.1.0")

    //viewpager2 可以禁止预加载
    api("androidx.viewpager2:viewpager2:1.0.0-beta04")

    //视频播放组件
    api("com.google.android.exoplayer:exoplayer-core:2.10.4")
    api("com.google.android.exoplayer:exoplayer-dash:2.10.4")
    api("com.google.android.exoplayer:exoplayer-ui:2.10.4")


    //room数据库
    api("android.arch.persistence.room:runtime:1.1.1")
    api("android.arch.lifecycle:extensions:1.1.1")
    annotationProcessor("android.arch.persistence.room:compiler:1.1.1")
    annotationProcessor("android.arch.lifecycle:compiler:1.1.1")


    //图片加载
    api("jp.wasabeef:glide-transformations:4.0.0")
    api("com.github.bumptech.glide:glide:4.9.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.9.0")

    //camera
    api("androidx.camera:camera-core:1.0.0-beta01")
    api("androidx.camera:camera-camera2:1.0.0-beta01")
    api("androidx.camera:camera-view:1.0.0-alpha08")
    api("androidx.camera:camera-extensions:1.0.0-alpha08")

    //workmanager
    api("androidx.work:work-runtime:2.2.0")

    //gesture imageview
    api("com.github.chrisbanes:PhotoView:2.3.0@aar")

    //aliyun oss
    api("com.aliyun.dpa:oss-android-sdk:+")
}

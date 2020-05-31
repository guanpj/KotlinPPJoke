// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
        maven("https://jitpack.io" )

        maven("http://maven.aliyun.com/nexus/content/repositories/google")
        maven("http://maven.aliyun.com/nexus/content/groups/public/")
        maven("http://maven.aliyun.com/nexus/content/repositories/jcenter")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.0")
        classpath(kotlin("gradle-plugin", version = "1.3.61"))

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://jitpack.io" )

        maven("http://maven.aliyun.com/nexus/content/repositories/google")
        maven("http://maven.aliyun.com/nexus/content/groups/public/")
        maven("http://maven.aliyun.com/nexus/content/repositories/jcenter")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}


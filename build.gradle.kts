// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val navigationVersion by extra("2.5.3")
    val hiltVersion by extra("2.44.2")

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navigationVersion")
        classpath("com.android.tools.build:gradle:7.3.1")
    }
}
plugins {
    id("com.android.application").version("7.3.1") apply false
    id("com.android.library").version("7.3.1") apply false
    id("org.jetbrains.kotlin.android").version("1.7.20") apply false
}

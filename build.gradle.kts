plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}
android {
    namespace = "com.example.chatgptkeyboard"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.example.chatgptkeyboard"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "0.1"
    }
}

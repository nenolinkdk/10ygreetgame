plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "dk.nenolink.birthdayblockquest"
    compileSdk = 35

    defaultConfig {
        applicationId = "dk.nenolink.birthdayblockquest"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
}

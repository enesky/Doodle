import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
}

android {
    namespace = "dev.enesky.doodle"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.enesky.doodle"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )

        // General Build Configs
        resValue("string", "app_name", "Doodle")
        buildConfigField("String", "BASE_URL", "\"url\"")
        buildConfigField("String", "example", "\"Lorem Ipsum\"")
    }

    signingConfigs {
        val keystoreProperties = Properties()
        val keystorePath = "../Doodle/keystore/keystore.properties"
        val keystorePropertiesFile = rootProject.file(keystorePath)
        FileInputStream(keystorePropertiesFile).use { keystoreProperties.load(it) }
        create("release") {
            // Change storeFile path if Doodle goes to any market
            storeFile = file(keystoreProperties["storeFile"].toString())
            storePassword = keystoreProperties["storePassword"].toString()
            keyAlias = keystoreProperties["keyAlias"].toString()
            keyPassword = keystoreProperties["keyPassword"].toString()
            enableV1Signing = true //Jar Signature
            enableV2Signing = true // Full APK Signature
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            signingConfig = signingConfigs.getByName("release")

            // Specified Build Configs
            buildConfigField("String", "example", "\"Lorem Ipsum but release\"")
        }
    }

    flavorDimensions += listOf("version", "mode")
    productFlavors {
        create("dev") {
            dimension = "version"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
        }
        create("prod") {
            dimension = "version"
            applicationIdSuffix = ".prod"
            versionNameSuffix = "-prod"
        }
        create("trial") {
            dimension = "mode"
            applicationIdSuffix = ".trial"
            versionNameSuffix = "-trial"
            resValue("string", "app_name", "Doodle Trial")
        }
        create("premium") {
            dimension = "mode"
            applicationIdSuffix = ".premium"
            versionNameSuffix = "-premium"
            resValue("string", "app_name", "Doodle Premium")

            // Specified Build Configs
            buildConfigField("String", "BASE_URL", "\"url but premium\"")
        }
    }

    // Compile Settings
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions.jvmTarget = "17"
    composeOptions.kotlinCompilerExtensionVersion = "1.5.3"

    buildFeatures{
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    ksp {
        arg("KOIN_CONFIG_CHECK", "true") // Activates compile time safety
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)

    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    implementation(libs.bundles.composeMaterials)
    debugImplementation(libs.bundles.composeDebug)

    val koinBom = platform(libs.koinBom)
    implementation(koinBom)
    implementation(libs.bundles.koinMaterials)

    testImplementation(libs.junit)
    testImplementation(koinBom)
    testImplementation(libs.bundles.koinTestMaterials)
    androidTestImplementation(composeBom)
    androidTestImplementation(libs.bundles.testing)
}
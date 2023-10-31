import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.detekt)
    id("kotlin-parcelize")
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
        resValue("string", "app_name_flavor", "Doodle")
        buildConfigField("String", "BASE_URL", "\"https://api.jikan.moe/v4/\"")
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
            buildConfigField("boolean", "logEnabled", "true")
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            signingConfig = signingConfigs.getByName("release")

            // Specified Build Configs
            buildConfigField("String", "example", "\"Lorem Ipsum but release\"")
            buildConfigField("boolean", "logEnabled", "false")
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
            resValue("string", "app_name_flavor", "Doodle Trial")
        }
        create("premium") {
            dimension = "mode"
            applicationIdSuffix = ".premium"
            versionNameSuffix = "-premium"
            resValue("string", "app_name_flavor", "Doodle Premium")

            // Specified Build Configs
            buildConfigField("String", "BASE_URL", "\"https://api.jikan.moe/v4/\"")
        }
    }

    // Compile Settings
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
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
        /**
         * Activates compile time safety
         * But this is not a guarantee that there are no problems you may encounter at run-time.
         **/
        arg("KOIN_CONFIG_CHECK", "true")
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)

    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    implementation(libs.bundles.compose.materials)

    val koinBom = platform(libs.koin.bom)
    implementation(koinBom)
    implementation(libs.bundles.koin.materials)

    val firebaseBom = platform(libs.firebase.bom)
    implementation(firebaseBom)
    implementation(libs.bundles.firebase.materials)

    implementation(libs.bundles.network)

    debugImplementation(libs.bundles.debug.implementations)
    releaseImplementation(libs.chucker.no.op)

    implementation(libs.bundles.third.party.libraries)
    implementation(libs.bundles.androidx.libraries)

    detektPlugins(libs.bundles.detekt.rules)

    testImplementation(libs.junit)
    testImplementation(koinBom)
    testImplementation(libs.bundles.koin.test.materials)
    androidTestImplementation(composeBom)
    androidTestImplementation(libs.bundles.testing)
}

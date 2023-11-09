package dev.enesky.build_logic.convention

import com.android.build.api.dsl.ApplicationDefaultConfig
import com.android.build.api.dsl.ApplicationExtension

/**
 * Created by Enes Kamil YILMAZ on 03/11/2023
 */

internal fun ApplicationExtension.getBuildTypes() =
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

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )

            // Specified Build Configs
            buildConfigField("String", "example", "\"Lorem Ipsum but release\"")
            buildConfigField("boolean", "logEnabled", "false")
        }
    }

internal fun ApplicationExtension.getProductFlavors() {
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

            // Specified Res Values
            resValue("string", "app_name_flavor", "Doodle Trial")
        }
        create("premium") {
            dimension = "mode"
            applicationIdSuffix = ".premium"
            versionNameSuffix = "-premium"

            // Specified Res Values
            resValue("string", "app_name_flavor", "Doodle Premium")

            // Specified Build Configs
            buildConfigField("String", "example", "\"Lorem Ipsum but premium\"")
        }
    }
}

internal fun ApplicationDefaultConfig.getGeneralBuildConfigs() {
    resValue("string", "app_name_flavor", "Doodle")
    buildConfigField("String", "example", "\"Lorem Ipsum\"")
}

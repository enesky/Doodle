/*
 *                          Copyright 2023
 *            Designed and developed by Enes Kamil Yılmaz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.enesky.build_logic.convention.helpers

import com.android.build.api.dsl.ApplicationDefaultConfig
import com.android.build.api.dsl.ApplicationExtension

internal fun ApplicationExtension.getBuildTypes() = buildTypes {
    debug {
        isMinifyEnabled = false
        isShrinkResources = false
        isDebuggable = true
        applicationIdSuffix = DoodleBuildType.DEBUG.applicationIdSuffix
        buildConfigField("boolean", "logEnabled", "true")
        resValue("string", "app_name_flavor", "Doodle -debug")
    }

    val release = getByName("release") {
        isMinifyEnabled = true
        isShrinkResources = true
        isDebuggable = false

        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro",
        )

        applicationIdSuffix = DoodleBuildType.RELEASE.applicationIdSuffix
        // Specified Build Configs
        buildConfigField("boolean", "logEnabled", "false")
        resValue("string", "app_name_flavor", "Doodle")
    }

    create("benchmark") {
        // Enable all the optimizations from release build through initWith(release).
        initWith(release)
        matchingFallbacks.add("release")
        signingConfig = signingConfigs.getByName("debug")
        // Only use benchmark proguard rules
        proguardFiles("benchmark-rules.pro")
        applicationIdSuffix = DoodleBuildType.BENCHMARK.applicationIdSuffix
    }
}

/* -> Removed for simplicity
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
*/

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
plugins {
    alias(libs.plugins.baseline.profile)
    id(libs.plugins.common.benchmark.test.get().pluginId)
}

android {
    namespace = "dev.enesky.benchmark"

    defaultConfig {
        minSdk = libs.versions.min.sdk.benchmark.get().toInt()
        testInstrumentationRunnerArguments["androidx.benchmark.suppressErrors"] = "EMULATOR,DEBUGGABLE"

        buildConfigField("String", "APP_BUILD_TYPE_SUFFIX", "\"\"")
    }

    buildFeatures.buildConfig = true

    buildTypes {
        // This benchmark buildType is used for benchmarking, and should function like your
        // release build (for example, with minification on). It"s signed with a debug key
        // for easy local/CI testing.
        create("benchmark") {
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks.add("release")
        }
    }

    // Use the same flavor dimensions as the application to allow generating Baseline Profiles on prod,
    // which is more close to what will be shipped to users (no fake data), but has ability to run the
    // benchmarks on demo, so we benchmark on stable data.
    /*
    flavorDimensions += listOf("version", "mode")
    productFlavors {
        create("dev") {
            dimension = "version"
            buildConfigField("String", "APP_FLAVOR_VERS_SUFFIX", "\".dev\"")
        }
        create("prod") {
            dimension = "version"
            buildConfigField("String", "APP_FLAVOR_VERS_SUFFIX", "\".prod\"")
        }
        create("trial") {
            dimension = "mode"
            buildConfigField("String", "APP_FLAVOR_MODE_SUFFIX", "\".trial\"")
        }
        create("premium") {
            dimension = "mode"
            buildConfigField("String", "APP_FLAVOR_MODE_SUFFIX", "\".premium\"")
        }
    }
    */

    testOptions.managedDevices.devices {
        create<com.android.build.api.dsl.ManagedVirtualDevice>("pixel7Api33") {
            device = "Pixel 7"
            apiLevel = 33
            systemImageSource = "aosp"
        }
    }

    targetProjectPath = ":app"
    experimentalProperties["android.experimental.self-instrumenting"] = true
}

baselineProfile {
    // This specifies the managed devices to use that you run the tests on.
    managedDevices += "pixel7Api33"

    // Don't use a connected device but rely on a GMD for consistency between local and CI builds.
    useConnectedDevices = false
}

dependencies {
    implementation(libs.bundles.testing)
    implementation(libs.ui.automator)
    implementation(libs.benchmark.macro.junit4)
    implementation(libs.profiler.installer)
    implementation(libs.chucker)
}

androidComponents {
    beforeVariants(selector().all()) {
        it.enable = it.buildType == "benchmark"
    }
}
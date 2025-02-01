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

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

/**
 * Configure Compose-specific options
 */
internal fun Project.configureAndroidCompose(commonExtension: CommonExtension<*, *, *, *, *>) =
    with(commonExtension) {
        apply(plugin = "org.jetbrains.kotlin.plugin.compose")

        defaultConfig.vectorDrawables.useSupportLibrary = true

        buildFeatures {
            compose = true
            buildConfig = true
        }

        dependencies {
            val composeBomPlatform = platform(libs.compose.bom.get().toString())
            implementation(composeBomPlatform)
            implementation(libs.bundles.compose.materials)
            androidTestImplementation(composeBomPlatform)

            implementation(libs.coil.compose)
            implementation(libs.bundles.accompanist.materials)

            implementation(libs.androidx.navigation.compose)
            implementation(libs.lifecycle.runtime.compose)
        }
    }

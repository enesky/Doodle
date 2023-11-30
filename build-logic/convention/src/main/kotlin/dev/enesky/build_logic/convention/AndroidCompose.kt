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
package dev.enesky.build_logic.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Configure Compose-specific options
 */
internal fun Project.configureAndroidCompose(commonExtension: CommonExtension<*, *, *, *, *>) =
    with(commonExtension) {
        defaultConfig.vectorDrawables.useSupportLibrary = true

        buildFeatures {
            compose = true
            buildConfig = true
        }

        composeOptions.kotlinCompilerExtensionVersion =
            libs.versions.compose.compiler.get().toString()

        dependencies {
            val composeBomPlatform = platform(libs.compose.bom.get().toString())
            add("implementation", composeBomPlatform)
            add("implementation", libs.bundles.compose.materials)
            add("androidTestImplementation", composeBomPlatform)

            add("implementation", libs.androidx.navigation.compose)
            add("implementation", libs.lifecycle.runtime.compose)
        }
    }

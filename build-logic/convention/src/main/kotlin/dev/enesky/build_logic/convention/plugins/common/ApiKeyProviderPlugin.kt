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
package dev.enesky.build_logic.convention.plugins.common

import com.android.build.gradle.LibraryExtension
import dev.enesky.build_logic.convention.getLocalProperties
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

/**
 * Configure Api Key Provider
 * -> Only for core/network/build.gradle.kts <-
 */
class ApiKeyProviderPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val localProperties = getLocalProperties(rootProject)
        val doodleApiUrl: String = checkNotNull(
            localProperties.getProperty("doodle.api.url") ?: System.getenv("DOODLE_API_URL") ?: "\"\"",
        )
        val doodleApiKey: String = checkNotNull(
            localProperties.getProperty("doodle.api.key") ?: System.getenv("DOODLE_API_KEY") ?: "\"\"",
        )

        with(extensions.getByType<LibraryExtension>()) {
            buildFeatures.buildConfig = true

            defaultConfig.buildConfigField("String", "DOODLE_API_URL", doodleApiUrl)
            defaultConfig.buildConfigField("String", "DOODLE_API_KEY", doodleApiKey)

            /**
             * Example usage of buildConfigField for different build types and flavors
             *
             * buildTypes {
             *     getByName("release") {
             *         buildConfigField("String", "DOODLE_API_URL", doodleApiUrlForRelease)
             *         buildConfigField("String", "DOODLE_API_KEY", doodleApiKeyForRelease)
             *     }
             * }
             *
             * productFlavors {
             *     getByName("premium") {
             *         buildConfigField("String", "DOODLE_API_URL", doodleApiUrlForPremium)
             *         buildConfigField("String", "DOODLE_API_KEY", doodleApiKeyForPremium)
             *     }
             * }
             */
        }
    }
}

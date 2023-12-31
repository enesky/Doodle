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

import com.android.build.gradle.TestExtension
import dev.enesky.build_logic.convention.helpers.androidTestImplementation
import dev.enesky.build_logic.convention.helpers.configureKotlinAndroid
import dev.enesky.build_logic.convention.helpers.libs
import dev.enesky.build_logic.convention.helpers.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/**
 * Configure Test libraries for Android
 * -> For benchmark/build.gradle.kts, not for other/build.gradle.kts <-
 */
class TestPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.plugins.android.test.get().pluginId)
            apply(libs.plugins.kotlin.android.get().pluginId)
        }

        extensions.configure<TestExtension> {
            configureKotlinAndroid(this)
            defaultConfig.targetSdk = libs.versions.target.sdk.get().toString().toInt()
        }

        dependencies {
            androidTestImplementation(libs.bundles.testing)
            androidTestImplementation(platform(libs.compose.bom))
            testImplementation(libs.junit)
            testImplementation(platform(libs.koin.bom))
            testImplementation(libs.bundles.koin.test.materials)
        }
    }
}

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
import dev.enesky.build_logic.convention.helpers.configureKotlinAndroid
import dev.enesky.build_logic.convention.helpers.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Configure Test libraries for Android
 * Todo: make this for all modules
 */
class TestPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.plugins.android.test.get().pluginId)
            apply(libs.plugins.kotlin.android.get().pluginId)
        }

        extensions.configure<TestExtension> {
            configureKotlinAndroid(this)
            defaultConfig.targetSdk = libs.versions.target.sdk.get().toInt()
        }
    }
}

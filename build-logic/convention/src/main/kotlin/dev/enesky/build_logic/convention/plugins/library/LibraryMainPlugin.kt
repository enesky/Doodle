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
package dev.enesky.build_logic.convention.plugins.library

import com.android.build.gradle.LibraryExtension
import dev.enesky.build_logic.convention.configureKotlinAndroid
import dev.enesky.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/**
 * Android Library Main Convention Plugin
 * -> For modules/build.gradle.kts, not for app/build.gradle.kts <-
 */
class LibraryMainPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.android.library.get().pluginId)
                apply(libs.plugins.kotlin.android.get().pluginId)
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = libs.versions.target.sdk.get().toString().toInt()
            }

            dependencies {
                add("implementation", libs.core.ktx)
                add("implementation", libs.material)
                add("implementation", libs.appcompat)
                add("implementation", libs.lifecycle.runtime.ktx)

                val koinBom = platform(libs.koin.bom)
                add("implementation", koinBom)
                add("implementation", libs.bundles.koin.materials)
            }
        }
    }
}

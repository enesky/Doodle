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

import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import dev.enesky.build_logic.convention.helpers.configureKotlinAndroid
import dev.enesky.build_logic.convention.helpers.disableUnnecessaryAndroidTests
import dev.enesky.build_logic.convention.helpers.implementation
import dev.enesky.build_logic.convention.helpers.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import java.io.File

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
                apply(libs.plugins.ksp.plugin.get().pluginId)
            }

            extensions.configure<LibraryExtension> {
                defaultConfig.targetSdk = libs.versions.target.sdk.get().toString().toInt()
                configureKotlinAndroid(this)

                // Add KSP source directories to the model to make them visible to Android Studio
                libraryVariants.all {
                    addJavaSourceFoldersToModel(
                        File(buildDir, "generated/ksp/$name/kotlin"),
                    )
                }
            }

            extensions.configure<LibraryAndroidComponentsExtension> {
                disableUnnecessaryAndroidTests(target)
            }

            dependencies {
                implementation(libs.core.ktx)
                implementation(libs.material)
                implementation(libs.appcompat)
                implementation(libs.lifecycle.runtime.ktx)

                val koinBom = platform(libs.koin.bom)
                implementation(koinBom)
                implementation(libs.bundles.koin.materials)
            }
        }
    }
}

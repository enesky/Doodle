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
package dev.enesky.build_logic.convention.plugins.app

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.AbstractAppExtension
import dev.enesky.build_logic.convention.helpers.configureKotlinAndroid
import dev.enesky.build_logic.convention.helpers.debugImplementation
import dev.enesky.build_logic.convention.helpers.getBuildTypes
import dev.enesky.build_logic.convention.helpers.getGeneralBuildConfigs
import dev.enesky.build_logic.convention.helpers.getProductFlavors
import dev.enesky.build_logic.convention.helpers.implementation
import dev.enesky.build_logic.convention.helpers.ksp
import dev.enesky.build_logic.convention.helpers.libs
import dev.enesky.build_logic.convention.helpers.releaseImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import java.io.File

/**
 * Configure Android Application-specific options
 *  -> Only for app/build.gradle.kts <-
 */
class AppMainPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.plugins.android.application.get().pluginId)
            apply(libs.plugins.kotlin.android.get().pluginId)
            apply(libs.plugins.ksp.plugin.get().pluginId)
        }

        extensions.configure<ApplicationExtension> {
            configureKotlinAndroid(this)

            defaultConfig {
                targetSdk = libs.versions.target.sdk.get().toString().toInt()

                getBuildTypes()
                getProductFlavors()
                getGeneralBuildConfigs()

                packaging.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }

        // Add KSP source directories to the model to make them visible to Android Studio
        extensions.configure<AbstractAppExtension> {
            applicationVariants.all {
                addJavaSourceFoldersToModel(
                    File(buildDir, "generated/ksp/$name/kotlin"),
                )
            }
        }

        dependencies {
            implementation(libs.core.ktx)
            implementation(libs.activity.compose)
            implementation(libs.material)
            implementation(libs.appcompat)
            implementation(libs.lifecycle.runtime.ktx)
            implementation(libs.splash.screen)

            debugImplementation(libs.leak.canary)
            debugImplementation(libs.chucker)
            releaseImplementation(libs.chucker.no.op)

            val koinBom = platform(libs.koin.bom)
            implementation(koinBom)
            implementation(libs.bundles.koin.materials)

            implementation(libs.compose.destinations.core)
            ksp(libs.compose.destinations.ksp)
        }
    }
}

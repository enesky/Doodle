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

    // Convention Plugins
    id(libs.plugins.app.main.get().pluginId)
    id(libs.plugins.app.compose.get().pluginId)
    id(libs.plugins.app.firebase.get().pluginId)
    id(libs.plugins.common.signing.config.get().pluginId)
}

android {
    namespace = "dev.enesky.doodle"

    defaultConfig {
        applicationId = "dev.enesky.doodle"
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        create("benchmark") {
            initWith(buildTypes.getByName("release"))
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
        }
    }
}

dependencies {
    // Module Implementations
    implementation(projects.core.common)
    implementation(projects.core.data)
    implementation(projects.core.designSystem)
    implementation(projects.core.domain)
    implementation(projects.core.network)
    implementation(projects.core.navigation)
    implementation(projects.core.ui)
    implementation(projects.feature.login)
    implementation(projects.feature.home)
    implementation(projects.feature.details)
    implementation(projects.feature.explore)
    implementation(projects.feature.myLists)
    implementation(projects.feature.settings)
    implementation(projects.feature.splash)
}

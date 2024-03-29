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
    // Convention Plugins
    id(libs.plugins.library.main.get().pluginId)
    id(libs.plugins.common.authentication.get().pluginId)

    // Kotlin Plugins
    id("kotlin-parcelize")
}

android.namespace = "dev.enesky.core.domain"

dependencies {
    implementation(libs.androidx.paging.compose)
    implementation(libs.retrofit.converter.gson)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.remote.config)

    // Module Implementations
    implementation(projects.core.common)
    implementation(projects.core.data)
    implementation(projects.core.network)
}

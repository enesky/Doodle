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
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import java.util.Properties

internal val Project.libs
    get() = the<LibrariesForLibs>()

internal fun CommonExtension<*, *, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) =
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)

internal fun getLocalProperties(rootProject: Project): Properties {
    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties") // It's ignored by git
    if (localPropertiesFile.exists() && localPropertiesFile.isFile) {
        localPropertiesFile.inputStream().use { input ->
            localProperties.load(input)
        }
    }
    return localProperties
}

// Dependency Handler helpers
internal fun DependencyHandlerScope.implementation(dependency: Any) = "implementation"(dependency)
internal fun DependencyHandlerScope.testImplementation(dependency: Any) = "testImplementation"(dependency)
internal fun DependencyHandlerScope.androidTestImplementation(dependency: Any) = "androidTestImplementation"(dependency)
internal fun DependencyHandlerScope.ksp(dependency: Any) = "ksp"(dependency)
internal fun DependencyHandlerScope.detektPlugins(dependency: Any) = "detektPlugins"(dependency)
internal fun DependencyHandlerScope.releaseImplementation(dependency: Any) = "releaseImplementation"(dependency)
internal fun DependencyHandlerScope.debugImplementation(dependency: Any) = "debugImplementation"(dependency)
internal fun DependencyHandlerScope.coreLibraryDesugaring(dependency: Any) = "coreLibraryDesugaring"(dependency)

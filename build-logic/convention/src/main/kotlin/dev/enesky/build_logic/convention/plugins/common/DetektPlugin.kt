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

import dev.enesky.build_logic.convention.libs
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import java.io.File

/**
 * Detekt Configurations
 *  to use it on normal mode -> ./gradlew detektAll
 *  to use it on steroids -> ./gradlew detektDevPremiumDebug
 *  to use it with auto correct enabled -> ./gradlew detektAll / detektDevPremiumDebug --auto-correct
 *  to use it to create a baseline -> ./gradlew detektCreateBaseline / detektBaselineDevPremiumDebug
 **/
class DetektPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        val sourcePath: String = rootDir.absolutePath
        val mainConfigFile = "$rootDir/config/detekt/config.yml"
        val composeConfigFile = "$rootDir/config/detekt/compose-config.yml"
        val baselineFile = "$rootDir/config/detekt/baseline.xml"
        val kotlinFiles = "**/*.kt"
        val resourceFiles = "**/resources/**"
        val buildFiles = "**/build/**"
        val generatedFiles = "**/generated/**"
        val ideRelatedFiles = "**/.idea/**"

        pluginManager.apply {
            apply(libs.plugins.detekt.plugin.get().pluginId)
        }

        dependencies {
            add("detektPlugins", libs.detekt.formatting.rules.get())
            add("detektPlugins", libs.detekt.compose.rules.get())
        }

        extensions.configure<DetektExtension> {
            tasks.withType<Detekt>().configureEach {
                include(kotlinFiles)
                exclude(resourceFiles, buildFiles, generatedFiles, ideRelatedFiles)
            }

            tasks.register<Detekt>("detektAll") {
                description = "Detects all detekt issues."
                setSource(sourcePath)
                config.setFrom(files(mainConfigFile, composeConfigFile))
                baseline.set(File(baselineFile))
                parallel = true
            }

            tasks.register<DetektCreateBaselineTask>("detektCreateBaseline") {
                description = "Creates a new detekt baseline."
                parallel.set(true)
                ignoreFailures.set(true)
                setSource(sourcePath)
                config.setFrom(files(mainConfigFile, composeConfigFile))
                baseline.set(File(baselineFile))
                include(kotlinFiles)
                exclude(resourceFiles, buildFiles, generatedFiles, ideRelatedFiles)
            }
        }
    }
}

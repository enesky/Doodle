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

import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.spotless.LineEnding
import dev.enesky.build_logic.convention.helpers.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Configure Spotless
 *  to check code format -> ./gradlew spotlessCheck
 *  to format code -> ./gradlew spotlessApply
 *  to check code format and format code -> ./gradlew spotlessCheck spotlessApply
 */
class SpotlessPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply(libs.plugins.spotless.plugin.get().pluginId)

        extensions.configure<SpotlessExtension> {
            kotlin {
                target("**/*.kt")
                targetExclude("build/**/*.kt", "**/build/**/*.kt", "config/**/*.kt", "${layout.buildDirectory}/**/*.kt")
                licenseHeaderFile(rootProject.file("config/spotless/licence.kt"))
                // Remove below in order to run licence header apply.
                // Somehow they are breaking it.
                ktlint().editorConfigOverride(
                    mapOf(
                        "indent_size" to "4",
                        "continuation_indent_size" to "4",
                        "ktlint_standard_function-naming" to "disabled",
                        "ktlint_standard_package-name" to "disabled",
                    ),
                )
                trimTrailingWhitespace()
                endWithNewline()
            }

            kotlinGradle {
                target("**/*.gradle.kts")
                targetExclude("build/**/*.gradle.kts", "**/build/**/*.gradle.kts")
                licenseHeaderFile(
                    rootProject.file("config/spotless/licence.kt"),
                    "(plugins |pluginManagement |import |@file)",
                )
            }

            format("xml") {
                target("**/*.xml")
                targetExclude(".idea/**.xml", "build/**/*.xml", "**/build/**/*.xml", "config/**/*.xml")
                licenseHeaderFile(rootProject.file("config/spotless/licence.xml"), "(<[^!?])")
            }

            lineEndings = LineEnding.PLATFORM_NATIVE
        }
    }
}

package dev.enesky.build_logic.convention.plugins.common

import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.spotless.LineEnding
import dev.enesky.build_logic.convention.libs
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
        ktlint().editorConfigOverride(
          mapOf(
            "indent_size" to "2",
            "continuation_indent_size" to "2",
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
        targetExclude("build/**/*.xml", "**/build/**/*.xml", "config/**/*.xml")
        licenseHeaderFile(rootProject.file("config/spotless/licence.xml"), "(<[^!?])")
      }

      lineEndings = LineEnding.PLATFORM_NATIVE
    }
  }
}

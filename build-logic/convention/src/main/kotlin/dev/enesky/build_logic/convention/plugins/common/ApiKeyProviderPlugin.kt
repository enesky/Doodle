package dev.enesky.build_logic.convention.plugins.common

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import java.util.Properties

/**
 * Configure Api Key Provider
 * -> Only for core/network/build.gradle.kts <-
 */
class ApiKeyProviderPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties") // It's ignored by git
        if (localPropertiesFile.exists() && localPropertiesFile.isFile) {
            localPropertiesFile.inputStream().use { input ->
                localProperties.load(input)
            }
        }

        val doodleApiUrl: String = checkNotNull(
            localProperties.getProperty("doodle.api.url") ?: System.getenv("DOODLE_API_URL") ?: "\"\"",
        )
        val doodleApiKey: String = checkNotNull(
            localProperties.getProperty("doodle.api.key") ?: System.getenv("DOODLE_API_KEY") ?: "\"\"",
        )

        with(extensions.getByType<LibraryExtension>()) {
            buildFeatures.buildConfig = true

            defaultConfig.buildConfigField("String", "DOODLE_API_URL", doodleApiUrl)
            defaultConfig.buildConfigField("String", "DOODLE_API_KEY", doodleApiKey)

            /**
             * Example usage of buildConfigField for different build types and flavors
             *
             * buildTypes {
             *     getByName("release") {
             *         buildConfigField("String", "DOODLE_API_URL", doodleApiUrlForRelease)
             *         buildConfigField("String", "DOODLE_API_KEY", doodleApiKeyForRelease)
             *     }
             * }
             *
             * productFlavors {
             *     getByName("premium") {
             *         buildConfigField("String", "DOODLE_API_URL", doodleApiUrlForPremium)
             *         buildConfigField("String", "DOODLE_API_KEY", doodleApiKeyForPremium)
             *     }
             * }
             */
        }
    }
}

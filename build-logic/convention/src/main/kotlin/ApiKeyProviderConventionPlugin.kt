import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.util.Properties

/**
 * Configure Api Key Provider
 * -> Only for app/build.gradle.kts <-
 */
class ApiKeyProviderConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties") // it's ignored by git
        if (localPropertiesFile.exists() && localPropertiesFile.isFile) {
            localPropertiesFile.inputStream().use { input ->
                localProperties.load(input)
            }
        }

        val doodleApiUrl: String = checkNotNull(
            localProperties.getProperty("doodle.api.url") ?: System.getenv("DOODLE_API_URL")
        )
        val doodleApiKey: String = checkNotNull(
            localProperties.getProperty("doodle.api.key") ?: System.getenv("DOODLE_API_KEY")
        )

        extensions.configure<BaseAppModuleExtension> {
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

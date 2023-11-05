import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.util.Properties

class ApiKeyProviderConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties") // it's ignored by git
        if (localPropertiesFile.exists() && localPropertiesFile.isFile) {
            localPropertiesFile.inputStream().use { input ->
                localProperties.load(input)
            }
        }

        val doodleApiUrl = checkNotNull(
            localProperties.getProperty("doodle.apiurl") ?: System.getenv("DOODLE_API_URL")
        )
        val doodleApiKey = checkNotNull(
            localProperties.getProperty("doodle.apikey") ?: System.getenv("DOODLE_API_KEY")
        )

        extensions.configure<BaseAppModuleExtension> {
            defaultConfig.buildConfigField("String", "DOODLE_API_URL", "\"$doodleApiUrl\"")
            defaultConfig.buildConfigField("String", "DOODLE_API_KEY", "\"$doodleApiKey\"")
        }
    }
}

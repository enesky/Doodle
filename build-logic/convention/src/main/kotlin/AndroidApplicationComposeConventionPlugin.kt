import com.android.build.api.dsl.ApplicationExtension
import dev.enesky.build_logic.convention.configureAndroidCompose
import dev.enesky.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Configure Compose for Android Application
 *   -> Only for app/build.gradle.kts <-
 */
class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(libs.plugins.android.application.get().pluginId)
            configureAndroidCompose(extensions.getByType<ApplicationExtension>())

            dependencies {

            }
        }
    }

}

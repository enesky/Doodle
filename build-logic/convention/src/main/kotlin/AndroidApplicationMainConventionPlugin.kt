import com.android.build.api.dsl.ApplicationExtension
import dev.enesky.build_logic.convention.configureKotlinAndroid
import dev.enesky.build_logic.convention.getBuildTypes
import dev.enesky.build_logic.convention.getGeneralBuildConfigs
import dev.enesky.build_logic.convention.getProductFlavors
import dev.enesky.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Configure Android Application-specific options
 */
class AndroidApplicationMainConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.plugins.android.application.get().pluginId)
            apply(libs.plugins.kotlin.android.get().pluginId)
            apply(libs.plugins.ksp.plugin.get().pluginId)
            apply("kotlin-parcelize")
        }

        extensions.configure<ApplicationExtension> {
            configureKotlinAndroid(this)

            defaultConfig {
                targetSdk = libs.versions.target.sdk.get().toString().toInt()

                getBuildTypes()
                getProductFlavors()
                getGeneralBuildConfigs()

                packaging.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }
}

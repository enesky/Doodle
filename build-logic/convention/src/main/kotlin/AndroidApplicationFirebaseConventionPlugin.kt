import com.android.build.api.dsl.ApplicationExtension
//import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import dev.enesky.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationFirebaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.google.services.get().pluginId)
                apply(libs.plugins.firebase.performance.get().pluginId)
                apply(libs.plugins.firebase.crashlytics.get().pluginId)
            }

            dependencies {
                add("implementation", platform(libs.firebase.bom))
                add("implementation", libs.firebase.analytics)
                add("implementation", libs.firebase.performance)
                add("implementation", libs.firebase.crashlytics)
            }

            extensions.configure<ApplicationExtension> {
                buildTypes.configureEach {
                    // Disable the Crashlytics mapping file upload. This feature should only be
                    // enabled if a Firebase backend is available and configured in
                    // google-services.json.
                    // configure<CrashlyticsExtension> {
                    //    mappingFileUploadEnabled = false
                    //}
                }
            }
        }
    }
}

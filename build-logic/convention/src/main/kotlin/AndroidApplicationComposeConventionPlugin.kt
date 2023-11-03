import com.android.build.api.dsl.ApplicationExtension
import dev.enesky.build_logic.convention.configureAndroidCompose
import dev.enesky.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(libs.plugins.android.application.get().pluginId)

            configureAndroidCompose(extensions.getByType<ApplicationExtension>())
        }
    }

}

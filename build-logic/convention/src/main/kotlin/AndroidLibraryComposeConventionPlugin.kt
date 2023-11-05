import com.android.build.gradle.LibraryExtension
import dev.enesky.build_logic.convention.configureAndroidCompose
import dev.enesky.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

/**
 * Configure Compose for Android Application
 * -> For modules/build.gradle.kts, not for app/build.gradle.kts <-
 */
class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(libs.plugins.android.library.get().pluginId)

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)
        }
    }

}

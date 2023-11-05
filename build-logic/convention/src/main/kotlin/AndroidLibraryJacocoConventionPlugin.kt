import com.android.build.api.variant.LibraryAndroidComponentsExtension
import dev.enesky.build_logic.convention.configureJacoco
import dev.enesky.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryJacocoConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.gradle.jacoco")
                apply(libs.plugins.android.library.get().pluginId)
            }
            val extension = extensions.getByType<LibraryAndroidComponentsExtension>()
            configureJacoco(extension)
        }
    }

}

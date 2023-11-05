import com.android.build.gradle.TestExtension
import dev.enesky.build_logic.convention.configureKotlinAndroid
import dev.enesky.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.test")
                apply(libs.plugins.kotlin.android.get().pluginId)
            }

            extensions.configure<TestExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = libs.versions.target.sdk.get().toString().toInt()
            }
        }
    }

}

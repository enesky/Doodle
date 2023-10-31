import dev.enesky.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("doodle.android.library")
                // apply("doodle.android.hilt")

                apply(libs.findLibrary("detekt-gradle-plugin").get().get().group.toString())
            }

            dependencies {

                // TODO: Add core modules here

                add("implementation", libs.findLibrary("coil.kt").get())
                add("implementation", libs.findLibrary("coil.kt.compose").get())

                add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())

                add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())

                add("detektPlugins", libs.findLibrary("detekt.formatting.rules").get())
                add("detektPlugins", libs.findLibrary("detekt.compose.rules").get())
                // "detektPlugins"(libs.findLibrary("detekt-formatting-rules").get())
                // "detektPlugins"(libs.findLibrary("detekt-compose-rules").get())
            }
        }
    }
}

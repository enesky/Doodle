import dev.enesky.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("kotlin-parcelize")

        dependencies {
            val koinBom = platform(libs.koin.bom)
            add("implementation", koinBom)
            add("implementation", libs.bundles.koin.materials)

            // Unit Test
            add("testImplementation", libs.junit)
            add("androidTestImplementation", libs.bundles.testing)
        }
    }
}

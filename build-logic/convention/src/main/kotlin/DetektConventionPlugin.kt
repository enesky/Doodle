import dev.enesky.build_logic.convention.libs
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import java.io.File

/**
 * Detekt Configurations
 *  to use it on normal mode -> ./gradlew detektAll
 *  to use it on steroids -> ./gradlew detektDevPremiumDebug
 *  to use it with auto correct enabled -> ./gradlew detekt / detektDevPremiumDebug --auto-correct
 *  to use it to create a baseline -> ./gradlew detekCreateBaseline / detektBaselineDevPremiumDebug
 **/

class DetektConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.run {

            val sourcePath: String = rootDir.absolutePath
            val mainConfigFile = "$rootDir/detekt/config.yml"
            val composeConfigFile = "$rootDir/detekt/compose-config.yml"
            val baselineFile = "$rootDir/detekt/baseline.xml"
            val kotlinFiles = "**/*.kt"
            val resourceFiles = "**/resources/**"
            val buildFiles = "**/build/**"
            val generatedFiles = "**/generated/**"
            val ideRelatedFiles = "**/.idea/**"

            pluginManager.apply {
                apply(libs.findLibrary("detekt-gradle-plugin").get().get().group.toString())
            }

            dependencies {
                "detektPlugins"(libs.findBundle("detekt-rules").get())
            }

            extensions.configure<DetektExtension> {

                tasks.withType<Detekt>().configureEach {
                    include(kotlinFiles)
                    exclude(resourceFiles, buildFiles, generatedFiles, ideRelatedFiles)
                    reports {
                        html.required.set(false)
                        xml.required.set(false)
                        txt.required.set(false)
                    }
                }

                tasks.register<Detekt>("detektAll") {
                    description = "Detects all detekt issues."
                    setSource(sourcePath)
                    config.setFrom(files(mainConfigFile, composeConfigFile))
                    baseline.set(File(baselineFile))
                    parallel = true
                }

                tasks.register<DetektCreateBaselineTask>("detektCreateBaseline") {
                    description = "Creates a new detekt baseline."
                    parallel.set(true)
                    ignoreFailures.set(true)
                    setSource(sourcePath)
                    config.setFrom(files(mainConfigFile, composeConfigFile))
                    baseline.set(File(baselineFile))
                    include(kotlinFiles)
                    exclude(resourceFiles, buildFiles, generatedFiles, ideRelatedFiles)
                }
            }
        }
    }

}

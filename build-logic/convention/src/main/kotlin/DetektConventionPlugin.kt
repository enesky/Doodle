import dev.enesky.build_logic.convention.libs
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.withType
import java.io.File

/**
 * Detekt Configurations
 *  to use it on normal mode -> ./gradlew detekt
 *  to use it on steroids -> ./gradlew detektDevPremiumDebug
 *  to use it with auto correct enabled -> ./gradlew detekt / detektDevPremiumDebug --auto-correct
 *  to use it to create a baseline -> ./gradlew detektBaseline / detektBaselineDevPremiumDebug
 **/

class DetektConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        val sourcePath: String = rootDir.absolutePath
        val mainConfigFile = "$rootDir/detekt/config.yml"
        val composeConfigFile = "$rootDir/detekt/compose-config.yml"
        val baselineFile = "$rootDir/detekt/baseline.xml"
        val kotlinFiles = "**/*.kt"
        val resourceFiles = "**/resources/**"
        val buildFiles = "**/build/**"
        val generatedFiles = "**/generated/**"
        val ideRelatedFiles = "**/.idea/**"

        pluginManager.apply(
            libs.findLibrary("detekt-gradle-plugin").get().get().group.toString()
        )

        dependencies {
            "detektPlugins"(libs.findBundle("detekt-rules").get())
        }

        extensions.configure<DetektExtension> {
            source.setFrom(fileTree(sourcePath))
            config.setFrom(files(mainConfigFile, composeConfigFile))
            baseline = File(baselineFile)
            ignoreFailures = false
            parallel = true
            buildUponDefaultConfig = false
        }

        tasks.withType<Detekt>().configureEach {
            include(kotlinFiles)
            exclude(resourceFiles, buildFiles, generatedFiles, ideRelatedFiles)
        }
    }

}

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.register

/**
 * Configure Git Hooks
 * -> Only for project/build.gradle.kts <-
 */

class GitHooksConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {

        apply(from = "git-hooks/githooks.gradle")

        tasks.register("clean", Delete::class) {
            delete(rootProject.layout.buildDirectory)
        }

        afterEvaluate {
            tasks.named("clean") {
                dependsOn(":installGitHooks")
            }
        }
    }
}

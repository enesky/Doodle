package dev.enesky.build_logic.convention.plugins.common

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

/**
 * Configure Git Hooks
 * -> Only for project/build.gradle.kts <-
 */
class GitHooksPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        apply(from = "git-hooks/githooks.gradle")

        /**
         * Uncomment this if you don't use Spotless
         * Spotless itself registers the clean task, so we need to delete it
         *
         * tasks.register("clean", Delete::class) {
         *     delete(rootProject.layout.buildDirectory)
         * }
         **/

        afterEvaluate {
            tasks.named("clean") {
                dependsOn(":installGitHooks")
            }
        }
    }
}

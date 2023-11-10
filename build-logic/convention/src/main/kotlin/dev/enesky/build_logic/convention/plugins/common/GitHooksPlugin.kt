/*
 *                          Copyright 2023
 *            Designed and developed by Enes Kamil Yılmaz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
        apply(from = "config/git-hooks/githooks.gradle")

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

package dev.enesky.build_logic.plugins.app

import dev.enesky.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AppFirebasePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.google.services.get().pluginId)
                apply(libs.plugins.firebase.performance.get().pluginId)
                apply(libs.plugins.firebase.crashlytics.get().pluginId)
            }

            dependencies {
                val firebaseBom = platform(libs.firebase.bom)
                add("implementation", firebaseBom)
                add("implementation", libs.bundles.firebase.materials)
            }
        }
    }
}

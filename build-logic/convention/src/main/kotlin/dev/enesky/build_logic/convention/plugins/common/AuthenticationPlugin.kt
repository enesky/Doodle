package dev.enesky.build_logic.convention.plugins.common

import dev.enesky.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Created by Enes Kamil YILMAZ on 19/11/2023
 */
class AuthenticationPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        dependencies {
            // Firebase Authentication
            add("implementation", platform(libs.firebase.bom))
            add("implementation", libs.firebase.authentication)

            // Google Sign In
            add("implementation", libs.google.auth)

            // Credential Manager
            add("implementation", libs.bundles.credential.manager)
        }
    }
}

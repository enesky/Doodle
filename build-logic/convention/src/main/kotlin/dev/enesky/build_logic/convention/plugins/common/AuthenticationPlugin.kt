package dev.enesky.build_logic.convention.plugins.common

import dev.enesky.build_logic.convention.implementation
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
            implementation(platform(libs.firebase.bom))
            implementation(libs.firebase.authentication)

            // Google Sign In
            implementation(libs.google.auth)

            // Credential Manager
            implementation(libs.bundles.credential.manager)
        }
    }
}

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

            // Credential Manager
            add("implementation", libs.credential.manager)
            add("implementation", libs.credential.manager.play.services.auth)
        }
    }
}
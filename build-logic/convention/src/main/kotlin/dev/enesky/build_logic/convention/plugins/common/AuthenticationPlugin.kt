package dev.enesky.build_logic.convention.plugins.common

import com.android.build.gradle.LibraryExtension
import dev.enesky.build_logic.convention.getLocalProperties
import dev.enesky.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Created by Enes Kamil YILMAZ on 19/11/2023
 */
class AuthenticationPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val localProperties = getLocalProperties(rootProject)
        val googleApiKey: String = checkNotNull(
            localProperties.getProperty("doodle.google.api.key")
                ?: System.getenv("DOODLE_GOOGLE_API_KEY")
                ?: "\"\"",
        )

        with(extensions.getByType<LibraryExtension>()) {
            buildFeatures.buildConfig = true
            defaultConfig.buildConfigField("String", "DOODLE_GOOGLE_API_KEY", googleApiKey)
        }

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

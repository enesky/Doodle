package dev.enesky.build_logic.plugins.library

import com.android.build.gradle.LibraryExtension
import dev.enesky.build_logic.convention.configureKotlinAndroid
import dev.enesky.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/**
 * Android Library Main Convention Plugin
 * -> For modules/build.gradle.kts, not for app/build.gradle.kts <-
 */
class LibraryMainPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.android.library.get().pluginId)
                apply(libs.plugins.kotlin.android.get().pluginId)
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = libs.versions.target.sdk.get().toString().toInt()
            }

            dependencies {
                add("implementation", libs.core.ktx)
                add("implementation", libs.material)
                add("implementation", libs.lifecycle.runtime.ktx)
            }
        }
    }
}

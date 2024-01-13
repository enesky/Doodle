package dev.enesky.build_logic.convention.plugins.common

import com.android.build.gradle.TestExtension
import dev.enesky.build_logic.convention.helpers.configureKotlinAndroid
import dev.enesky.build_logic.convention.helpers.implementation
import dev.enesky.build_logic.convention.helpers.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/**
 * Configure Test libraries for Android
 * -> For benchmark/build.gradle.kts, not for other/build.gradle.kts <-
 */
class BenchmarkTestPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.plugins.android.test.get().pluginId)
            apply(libs.plugins.kotlin.android.get().pluginId)
        }

        extensions.configure<TestExtension> {
            configureKotlinAndroid(this)
            defaultConfig.targetSdk = libs.versions.target.sdk.get().toInt()
        }

        dependencies {
            implementation(libs.bundles.testing)
            implementation(platform(libs.compose.bom))
            implementation(libs.junit)
            implementation(platform(libs.koin.bom))
            implementation(libs.bundles.koin.test.materials)
        }
    }
}

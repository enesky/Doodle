package dev.enesky.build_logic.convention.plugins.common

import com.android.build.gradle.TestExtension
import dev.enesky.build_logic.convention.configureKotlinAndroid
import dev.enesky.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/**
 * Configure Test libraries for Android
 * -> For benchmark/build.gradle.kts, not for other/build.gradle.kts <-
 */
class TestPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.plugins.android.test.get().pluginId)
            apply(libs.plugins.kotlin.android.get().pluginId)
        }

        extensions.configure<TestExtension> {
            configureKotlinAndroid(this)
            defaultConfig.targetSdk = libs.versions.target.sdk.get().toString().toInt()
        }

        dependencies {
            add("testImplementation", libs.junit)
            add("androidTestImplementation", libs.bundles.testing)
            add("androidTestImplementation", platform(libs.compose.bom))
            add("testImplementation", platform(libs.koin.bom))
            add("testImplementation", libs.bundles.koin.test.materials)
        }
    }
}

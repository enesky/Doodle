package dev.enesky.build_logic.convention.plugins.app

import com.android.build.api.dsl.ApplicationExtension
import dev.enesky.build_logic.convention.configureKotlinAndroid
import dev.enesky.build_logic.convention.getBuildTypes
import dev.enesky.build_logic.convention.getGeneralBuildConfigs
import dev.enesky.build_logic.convention.getProductFlavors
import dev.enesky.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/**
 * Configure Android Application-specific options
 *  -> Only for app/build.gradle.kts <-
 */
class AppMainPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.plugins.android.application.get().pluginId)
            apply(libs.plugins.kotlin.android.get().pluginId)
            apply(libs.plugins.ksp.plugin.get().pluginId)
            apply(libs.plugins.common.feature.get().pluginId)
        }

        extensions.configure<ApplicationExtension> {
            configureKotlinAndroid(this)

            defaultConfig {
                targetSdk = libs.versions.target.sdk.get().toString().toInt()

                getBuildTypes()
                getProductFlavors()
                getGeneralBuildConfigs()

                packaging.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }

        dependencies {
            add("implementation", libs.core.ktx)
            add("implementation", libs.activity.compose)
            add("implementation", libs.material)
            add("implementation", libs.appcompat)
            add("implementation", libs.lifecycle.runtime.ktx)

            add("debugImplementation", libs.leak.canary)
            add("debugImplementation", libs.chucker)
            add("releaseImplementation", libs.chucker.no.op)
        }
    }
}

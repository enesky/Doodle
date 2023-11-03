package dev.enesky.build_logic.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Configure Compose-specific options
 */
internal fun Project.configureAndroidCompose(commonExtension: CommonExtension<*, *, *, *, *>) =
    with(commonExtension) {
        defaultConfig.vectorDrawables.useSupportLibrary = true

        buildFeatures {
            compose = true
            buildConfig = true
        }

        composeOptions.kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get().toString()

        dependencies {
            val bom = libs.compose.bom.get().toString()
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))
        }
    }
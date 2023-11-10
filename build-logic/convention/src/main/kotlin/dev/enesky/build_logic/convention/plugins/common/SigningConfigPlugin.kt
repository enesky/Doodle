/*
 *                          Copyright 2023
 *            Designed and developed by Enes Kamil Yılmaz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.enesky.build_logic.convention.plugins.common
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import dev.enesky.build_logic.convention.createSigningConfigFromProperties
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.util.Properties

/**
 * Configure Android Signing Config-specific options
 *      -> Only for app/build.gradle.kts <-
 */
class SigningConfigPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val keystoreProperties = Properties()
        val keystorePath = "../Doodle/keystore/keystore.properties"
        val keystorePropertiesFile = rootProject.file(keystorePath)
        if (keystorePropertiesFile.exists() && keystorePropertiesFile.isFile) {
            keystorePropertiesFile.inputStream().use { input ->
                keystoreProperties.load(input)
            }
        }

        extensions.configure<BaseAppModuleExtension> {
            val debugSigningConfig = signingConfigs.getByName("debug")
            val releaseSigningConfig = createSigningConfigFromProperties(
                target = this@with,
                name = "release",
                properties = keystoreProperties,
            )

            defaultConfig {
                buildTypes {
                    release {
                        signingConfig = releaseSigningConfig ?: debugSigningConfig
                    }
                }
            }
        }
    }
}

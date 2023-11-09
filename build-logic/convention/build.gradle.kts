/*
 * Copyright 2023 Enes Kamil Yılmaz
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
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "dev.enesky.build_logic.convention"

java {
    sourceCompatibility = JavaVersion.toVersion(libs.versions.jvm.get())
    targetCompatibility = JavaVersion.toVersion(libs.versions.jvm.get())
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = libs.versions.jvm.get().toString()
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.ksp.gradle.plugin)
    compileOnly(libs.detekt.gradle.plugin)
    compileOnly(libs.spotless.gradle.plugin)

    enableVersionCatalogAccess()
}

/**
 * Little note about convention plugin id naming
 *
 * All plugin id's have to be UNIQUE
 * If a id starts with something like this -> doodle.android.application
 * And there is a different id like this -> doodle.android.application.compose
 * First one will give you troubles when you try to use it
 * Because it may recognized as the second one and it can throw an error
 * Try to use unique id's like -> doodle.android.application.main instead of doodle.android.application
 **/

gradlePlugin {
    plugins {
        val rootPath = "dev.enesky.build_logic.convention.plugins"

        /**
         * App Related Convention Plugins
         * -> For app/build.gradle.kts, not for modules/build.gradle.kts <-
         */
        register("appMain") {
            id = libs.plugins.app.main.get().pluginId
            implementationClass = "$rootPath.app.AppMainPlugin"
        }
        register("appCompose") {
            id = libs.plugins.app.compose.get().pluginId
            implementationClass = "$rootPath.app.AppComposePlugin"
        }
        register("appJacoco") {
            id = libs.plugins.app.jacoco.get().pluginId
            implementationClass = "$rootPath.app.AppJacocoPlugin"
        }
        register("appFirebase") {
            id = libs.plugins.app.firebase.get().pluginId
            implementationClass = "$rootPath.app.AppFirebasePlugin"
        }

        /**
         * Library Related Convention Plugins
         * -> For modules/build.gradle.kts, not for app/build.gradle.kts <-
         */
        register("libraryMain") {
            id = libs.plugins.library.main.get().pluginId
            implementationClass = "$rootPath.library.LibraryMainPlugin"
        }
        register("libraryCompose") {
            id = libs.plugins.library.compose.get().pluginId
            implementationClass = "$rootPath.library.LibraryComposePlugin"
        }
        register("libraryJacoco") {
            id = libs.plugins.library.jacoco.get().pluginId
            implementationClass = "$rootPath.library.LibraryJacocoPlugin"
        }

        /**
         * Common Convention Plugins
         * -> For both app/build.gradle.kts and modules/build.gradle.kts <-
         */
        register("commonFeature") {
            id = libs.plugins.common.feature.get().pluginId
            implementationClass = "$rootPath.common.FeaturePlugin"
        }
        register("commonTest") {
            id = libs.plugins.common.test.get().pluginId
            implementationClass = "$rootPath.common.TestPlugin"
        }
        register("commonSigningConfig") {
            id = libs.plugins.common.signing.config.get().pluginId
            implementationClass = "$rootPath.common.SigningConfigPlugin"
        }
        register("commonApiKeyProvider") {
            id = libs.plugins.common.api.key.provider.get().pluginId
            implementationClass = "$rootPath.common.ApiKeyProviderPlugin"
        }
        register("commonGitHooks") {
            id = libs.plugins.common.git.hooks.get().pluginId
            implementationClass = "$rootPath.common.GitHooksPlugin"
        }
        register("commonDetekt") {
            id = libs.plugins.common.detekt.get().pluginId
            implementationClass = "$rootPath.common.DetektPlugin"
        }
        register("commonSpotless") {
            id = libs.plugins.common.spotless.get().pluginId
            implementationClass = "$rootPath.common.SpotlessPlugin"
        }
    }
}

/**
 *                          !!! IMPORTANT !!!
 *
 *  This is needed in order to properly use libs extension in convention plugins
 *  and use convention plugins from version catalog in other modules
 *
 *  ex. Extensions/libs, VersionCatalog access on plugin aliases in every module's build.gradle.kts
 */
fun DependencyHandlerScope.enableVersionCatalogAccess() {
    compileOnly(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
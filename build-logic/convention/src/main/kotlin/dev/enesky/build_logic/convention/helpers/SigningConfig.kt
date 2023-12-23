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
package dev.enesky.build_logic.convention.helpers

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.android.build.gradle.internal.dsl.SigningConfig
import org.gradle.api.Project
import java.util.Properties

/**
 * Created by Enes Kamil YILMAZ on 05/11/2023
 */

internal fun BaseAppModuleExtension.createSigningConfigFromProperties(
    target: Project,
    name: String,
    properties: Properties,
): SigningConfig? {
    /**
     *                 !!! IMPORTANT !!!
     * Change storeFile path if Doodle goes to any market
     **/
    val keystore = mapOf(
        "storeFile" to properties.getProperty("storeFile").toString(),
        "storePassword" to properties.getProperty("storePassword").toString(),
        "keyAlias" to properties.getProperty("keyAlias").toString(),
        "keyPassword" to properties.getProperty("keyPassword").toString(),
    )

    if (keystore.values.any(String::isNullOrBlank)) return null

    return signingConfigs.create(name) {
        keyAlias = keystore.getValue("keyAlias")
        keyPassword = keystore.getValue("keyPassword")
        storeFile = target.file(keystore.getValue("storeFile"))
        storePassword = keystore.getValue("storePassword")
        enableV1Signing = true // Jar Signature
        enableV2Signing = true // Full APK Signature
    }
}

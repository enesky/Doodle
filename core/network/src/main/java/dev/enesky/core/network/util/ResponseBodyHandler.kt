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
package dev.enesky.core.network.util

import retrofit2.Response

/**
 * Created by Enes Kamil YILMAZ on 28/10/2023
 */

inline fun <reified T : Any> Response<T>.getBodyOrThrowError(): T {
    val errorMessage = "Response body is null."

    return if (isSuccessful) {
        body() ?: throw IllegalStateException(errorMessage)
    } else {
        throw IllegalStateException(errorMessage)
    }
}

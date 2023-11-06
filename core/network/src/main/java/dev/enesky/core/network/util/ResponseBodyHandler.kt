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

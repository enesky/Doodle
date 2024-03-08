package dev.enesky.core.common.consts

/**
 * Created by Enes Kamil YILMAZ on 29/11/2023
 */

// TODO: add context to this class and use it to get string resources
object ErrorMessages {

    const val GENERAL_ERROR = "An error occurred. Please try again later."
    const val NO_INTERNET_CONNECTION = "No internet connection. Please try again later."
}

/**
 * General error handler
 * @param throwable: Throwable
 */
fun getErrorMessage(throwable: Throwable?): String {
    return when {
        throwable?.localizedMessage != null -> throwable.localizedMessage.toString()
        throwable?.message != null -> throwable.message.toString()
        else -> ErrorMessages.GENERAL_ERROR
    }
}

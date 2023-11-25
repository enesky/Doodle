package dev.enesky.core.common.utils

import android.util.Log
import dev.enesky.core.common.BuildConfig

/**
 * Created by Enes Kamil YILMAZ on 22/11/2023
 */

/**
 * Custom loggers with debug check
 */
object Logger {
    fun error(
        tag: String,
        message: String,
        e: Throwable? = null,
    ) {
        if (!BuildConfig.DEBUG) return
        Log.e(tag, message, e)
    }

    fun info(
        tag: String,
        message: String,
        e: Throwable? = null,
    ) {
        if (!BuildConfig.DEBUG) return
        Log.i(tag, message, e)
    }
}

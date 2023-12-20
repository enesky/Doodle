package dev.enesky.core.network.interceptor

import dev.enesky.core.common.utils.Logger
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Enes Kamil YILMAZ on 20/12/2023
 */

/**
 * Jikan API has rate limit. This interceptor will handle 429 error.
 *
 * Duration	Requests
 * Daily	Unlimited
 * Per Minute	60 requests
 * Per Second	3 requests
 *
 * To be able to overcome this limit (especially second limiter),
 * When we see HTTP Code 429 (which is Too Many Requests) we need to wait 2 seconds and try again.
 */
class RateLimitInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)

        var tryCount = 0
        val maxLimit = 3
        val sleepTime = 2000L
        val tooManyRequestsHttpCode = 429

        while (!response.isSuccessful && response.code == tooManyRequestsHttpCode && tryCount < maxLimit) {
            Logger.debug(this.javaClass.simpleName, "Rate limit exceeded. Waiting 2 seconds...")
            Logger.debug(this.javaClass.simpleName, "Intercepted ${tryCount + 1} time")

            tryCount++
            response.close()
            try {
                Thread.sleep(sleepTime)
            } catch (e: InterruptedException) {
                Logger.debug(this.javaClass.simpleName, "Exception: ${e.message}")
            }
            response = chain.proceed(request)
        }

        return response
    }
}

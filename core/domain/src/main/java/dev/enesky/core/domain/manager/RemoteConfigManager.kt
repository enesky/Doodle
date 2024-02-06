package dev.enesky.core.domain.manager

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dev.enesky.core.common.BuildConfig
import dev.enesky.core.domain.manager.RemoteConfigKeys.HOME_SCREEN_PREVIEW_ANIME_ID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by Enes Kamil YILMAZ on 03/02/2024
 */

object RemoteConfigManager {

    private val _configStatus = MutableStateFlow(FetchStatus.LOADING)
    val configStatus: StateFlow<FetchStatus>
        get() = _configStatus

    private val remoteConfig by lazy {
        Firebase.remoteConfig
    }

    suspend fun init() {
        val fetchInterval: Long = if (BuildConfig.DEBUG) {
            10 // Fetch every 10 seconds in debug
        } else {
            60 * 60 * 1 // Fetch every 1 hour in production
        }
        val fetchRetry = 30L

        remoteConfig.apply {
            setConfigSettingsAsync(
                remoteConfigSettings {
                    minimumFetchIntervalInSeconds = fetchInterval
                    fetchTimeoutInSeconds = fetchRetry
                }
            )
            setDefaultsAsync(
                mutableMapOf<String, Any>(
                    HOME_SCREEN_PREVIEW_ANIME_ID to "10",
                )
            )
        }
        // Fetch remote config
        fetchRemoteConfig()
    }

    /**
     * Fetch remote config and update the status
     */
    private suspend fun fetchRemoteConfig() {
        _configStatus.value = FetchStatus.LOADING
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _configStatus.value = FetchStatus.SUCCESS
            } else {
                _configStatus.value = FetchStatus.ERROR
            }
        }
    }

    val homeScreenPreviewAnimeId: Int
        get() = remoteConfig.getLong(HOME_SCREEN_PREVIEW_ANIME_ID).toInt()

}

object RemoteConfigKeys {
    const val HOME_SCREEN_PREVIEW_ANIME_ID = "home_screen_preview_anime_id"
}

enum class FetchStatus {
    LOADING,
    SUCCESS,
    ERROR
}

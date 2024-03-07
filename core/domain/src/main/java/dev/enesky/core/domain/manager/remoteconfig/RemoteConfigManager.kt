package dev.enesky.core.domain.manager.remoteconfig

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dev.enesky.core.common.BuildConfig
import dev.enesky.core.domain.manager.remoteconfig.RemoteConfigArgs.Keys.HOME_SCREEN_PREVIEW_ANIME_ID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by Enes Kamil YILMAZ on 03/02/2024
 */

object RemoteConfigManager {

    val configStatus: StateFlow<FetchStatus>
        get() = _configStatus
    private val _configStatus = MutableStateFlow(FetchStatus.LOADING)
    private val remoteConfig by lazy { Firebase.remoteConfig }
    private const val FETCH_INTERVAL_DEBUG: Long = 10 // Fetch every 10 seconds in debug
    private const val FETCH_INTERVAL: Long = 60 * 60 * 1 // Fetch every 1 hour
    private const val FETCH_RETRY: Long = 30

    object Values {
        val homeScreenPreviewAnimeId: Int
            get() = remoteConfig.getLong(HOME_SCREEN_PREVIEW_ANIME_ID).toInt()
    }

    /**
     * Initialize Remote Config with default values and fetch the remote config
     */
    suspend fun init() {
        val fetchInterval: Long = if (BuildConfig.DEBUG) FETCH_INTERVAL_DEBUG else FETCH_INTERVAL

        remoteConfig.apply {
            setConfigSettingsAsync(
                remoteConfigSettings {
                    minimumFetchIntervalInSeconds = fetchInterval
                    fetchTimeoutInSeconds = FETCH_RETRY
                },
            )
            setDefaultsAsync(
                mutableMapOf<String, Any>(
                    HOME_SCREEN_PREVIEW_ANIME_ID to RemoteConfigArgs.DefaultValues.VINLAND_SAGA_SEASON_2_ANIME_ID,
                ),
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
}

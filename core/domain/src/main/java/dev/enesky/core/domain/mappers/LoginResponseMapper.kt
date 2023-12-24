package dev.enesky.core.domain.mappers

import dev.enesky.core.common.utils.Constants
import dev.enesky.core.data.response.LoginResponse
import dev.enesky.core.domain.models.LoginResult

/**
 * Created by Enes Kamil YILMAZ on 24/12/2023
 */

fun LoginResponse.asLoginResult() = LoginResult(
    userId = user?.userId,
    username = user?.username,
    email = user?.email,
    profilePictureUrl = user?.profilePictureUrl ?: Constants.PLACEHOLDER_IMG_URL,
    errorMessage = errorMessage,
    isSignInSuccessful = user != null,
)

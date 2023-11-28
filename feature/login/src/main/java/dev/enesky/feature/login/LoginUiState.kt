package dev.enesky.feature.login

import dev.enesky.core.common.delegate.IEvent
import dev.enesky.core.common.delegate.IUiState

/**
 * Created by Enes Kamil YILMAZ on 21/11/2023
 */

data class LoginUiState(
    val loading: Boolean = false,
    val authType: AuthType = AuthType.ANONYMOUS,
    val signInResult: SignInResult? = null,
) : IUiState

enum class AuthType {
    ANONYMOUS,
    GOOGLE,
    EMAIL,
}

data class SignInResult(
    val data: UserData? = null,
    val errorMessage: String? = null,
)

data class UserData(
    val userId: String,
    val username: String? = null,
    val email: String? = null,
    val profilePictureUrl: String? = null,
)

sealed interface LoginEvents : IEvent {
    data class OnError(val errorMessage: String) : LoginEvents
    data object NavigateToHome : LoginEvents
}

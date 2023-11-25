package dev.enesky.feature.login

import dev.enesky.core.common.delegate.IUiState

/**
 * Created by Enes Kamil YILMAZ on 21/11/2023
 */

data class LoginUiState(
    val isFirstTime: Boolean = true,
    val loading: Boolean = false,
    val authType: AuthType = AuthType.ANONYMOUS,
    val signInResult: SignInResult? = null,
    val isSignInSuccessful: Boolean = false,
    val navigateToHome: Boolean = false,
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

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
    val signInError: String? = null,
    val email: String? = null,
    val password: String? = null,
    val navigateToHome: Boolean = false,
    val errorMessages: List<String> = listOf(),
) : IUiState

enum class AuthType {
    ANONYMOUS,
    GOOGLE,
    EMAIL,
}

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?,
)

data class UserData(
    val userId: String,
    val username: String?,
    val profilePictureUrl: String?,
)

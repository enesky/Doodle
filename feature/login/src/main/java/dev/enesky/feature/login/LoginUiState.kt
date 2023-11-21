package dev.enesky.feature.login

import dev.enesky.core.common.delegate.IUiState

/**
 * Created by Enes Kamil YILMAZ on 21/11/2023
 */

data class LoginUiState(
    val isFirstTime: Boolean = true,
    val loading: Boolean = false,
    val authType: AuthType = AuthType.ANONYMOUS,
    val email: String? = null,
    val password: String? = null,
    val navigateToHome: Boolean = false,
    val errorMessages: List<String> = listOf()
): IUiState

enum class AuthType {
    ANONYMOUS,
    GOOGLE,
    EMAIL
}

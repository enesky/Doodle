package dev.enesky.feature.login.signup.helpers

import dev.enesky.core.common.delegate.IUiState
import dev.enesky.core.data.AuthType
import dev.enesky.core.data.LoginResult

/**
 * Created by Enes Kamil YILMAZ on 29/11/2023
 */

data class SignUpUiState(
    val loading: Boolean = false,
    val authType: AuthType = AuthType.EMAIL,
    val loginResult: LoginResult? = null,
) : IUiState
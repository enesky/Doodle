package dev.enesky.feature.login.signin.helpers

import dev.enesky.core.common.delegate.IUiState
import dev.enesky.core.data.AuthType
import dev.enesky.core.data.LoginResult

/**
 * Created by Enes Kamil YILMAZ on 21/11/2023
 */

data class SignInUiState(
    val loading: Boolean = false,
    val authType: AuthType = AuthType.ANONYMOUS,
    val loginResult: LoginResult? = null,
) : IUiState

package dev.enesky.feature.login.signin.helpers

import dev.enesky.core.common.delegate.IUiState
import dev.enesky.core.data.LoginResult
import dev.enesky.core.data.LoginType

/**
 * Created by Enes Kamil YILMAZ on 21/11/2023
 */

data class SignInUiState(
    override val loading: Boolean = false,
    val loginType: LoginType = LoginType.ANONYMOUS,
    val loginResult: LoginResult? = null,
) : IUiState

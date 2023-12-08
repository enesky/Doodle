package dev.enesky.feature.login.signup.helpers

import dev.enesky.core.common.delegate.IUiState
import dev.enesky.core.data.LoginResult
import dev.enesky.core.data.LoginType

/**
 * Created by Enes Kamil YILMAZ on 29/11/2023
 */

data class SignUpUiState(
    override val loading: Boolean = false,
    val loginType: LoginType = LoginType.EMAIL,
    val loginResult: LoginResult? = null,
) : IUiState

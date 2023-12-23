package dev.enesky.feature.login.signin.helpers

import dev.enesky.core.common.delegate.IUiState
import dev.enesky.core.data.response.LoginResponse
import dev.enesky.core.data.response.LoginType

/**
 * Created by Enes Kamil YILMAZ on 21/11/2023
 */

data class SignInUiState(
    override val loading: Boolean = false,
    override var errorMessage: String? = null,
    val loginType: LoginType = LoginType.ANONYMOUS,
    val loginResult: LoginResponse? = null,
) : IUiState

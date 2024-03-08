package dev.enesky.feature.login.signin.helpers

import dev.enesky.core.common.delegate.IUiState
import dev.enesky.core.common.enums.LoginType
import dev.enesky.core.domain.models.LoginResult

/**
 * Created by Enes Kamil YILMAZ on 21/11/2023
 */

data class SignInUiState(
    override val isLoading: Boolean = false,
    val loginType: LoginType = LoginType.ANONYMOUS,
    val loginResult: LoginResult? = null,
) : IUiState

package dev.enesky.feature.login.signup.helpers

import dev.enesky.core.common.delegate.IUiState
import dev.enesky.core.common.enums.LoginType
import dev.enesky.core.domain.models.LoginResult

/**
 * Created by Enes Kamil YILMAZ on 29/11/2023
 */

data class SignUpUiState(
    override val loading: Boolean = false,
    override var errorMessage: String? = null,
    val loginType: LoginType = LoginType.EMAIL,
    val loginResult: LoginResult? = null,
) : IUiState

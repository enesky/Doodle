package dev.enesky.core.domain.models

/**
 * Created by Enes Kamil YILMAZ on 24/12/2023
 */

data class LoginResult(
    val userId: String? = null,
    val username: String? = null,
    val email: String? = null,
    val profilePictureUrl: String? = null,
    val errorMessage: String? = null,
    val isSignInSuccessful: Boolean? = false,
)

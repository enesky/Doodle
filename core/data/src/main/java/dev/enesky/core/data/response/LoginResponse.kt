package dev.enesky.core.data.response

/**
 * Created by Enes Kamil YILMAZ on 29/11/2023
 */

data class LoginResponse(
    val user: User? = null,
    val errorMessage: String? = null,
)

data class User(
    val userId: String,
    val username: String? = null,
    val email: String? = null,
    val profilePictureUrl: String? = null,
)

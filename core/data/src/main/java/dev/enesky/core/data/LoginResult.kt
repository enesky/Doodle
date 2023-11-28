package dev.enesky.core.data

/**
 * Created by Enes Kamil YILMAZ on 29/11/2023
 */

data class LoginResult(
    val data: UserData? = null,
    val errorMessage: String? = null,
)

data class UserData(
    val userId: String,
    val username: String? = null,
    val email: String? = null,
    val profilePictureUrl: String? = null,
)

enum class AuthType {
    ANONYMOUS,
    GOOGLE,
    EMAIL,
}


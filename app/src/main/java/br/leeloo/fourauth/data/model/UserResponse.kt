package br.leeloo.fourauth.data.model

import kotlinx.serialization.SerialName

internal data class UserResponse(
    @SerialName("uid")
    val uid: String? = "",
    @SerialName("email")
    val email: String? = "",
    @SerialName("password")
    val password: String? = "",
)

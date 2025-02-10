package br.leeloo.fourauth.data.mapper

import br.leeloo.fourauth.data.model.UserResponse
import br.leeloo.fourauth.domain.model.User
import com.google.firebase.auth.FirebaseUser

internal object AuthUserMapper {
    internal fun UserResponse.toDomain(): User = User(
        uid = uid.orEmpty(),
        email = email.orEmpty(),
        password = password.orEmpty()
    )

    fun FirebaseUser.toUserResponse(): UserResponse {
        return UserResponse(
            email = this.email, uid = this.uid
        )
    }
}

package br.leeloo.fourauth.domain.repository

import br.leeloo.fourauth.domain.model.User
import kotlinx.coroutines.flow.Flow

internal interface AuthUserRepository {
    fun login(email: String, password: String): Flow<Result<User>>
    fun logout()
    fun register(email: String, password: String): Flow<Result<User>>
}

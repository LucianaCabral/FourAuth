package br.leeloo.fourauth.data.source

import br.leeloo.fourauth.domain.model.User
import kotlinx.coroutines.flow.Flow

internal interface AuthUserDataSource {
    fun login(email: String, password: String): Flow<Result<User>>
    fun logout()
    fun register(email: String, password: String): Flow<Result<User>>
}
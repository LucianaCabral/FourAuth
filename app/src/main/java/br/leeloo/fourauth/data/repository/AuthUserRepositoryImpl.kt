package br.leeloo.fourauth.data.repository

import br.leeloo.fourauth.data.source.AuthUserDataSource
import br.leeloo.fourauth.domain.model.User
import br.leeloo.fourauth.domain.repository.AuthUserRepository
import kotlinx.coroutines.flow.Flow

internal class AuthUserRepositoryImpl(
    private val authUserDataSource: AuthUserDataSource,
) : AuthUserRepository {

    override  fun login(email: String, password: String): Flow<Result<User>> =
        authUserDataSource.login(email, password)

    override  fun logout() = authUserDataSource.logout()

    override  fun register(email: String, password: String):
            Flow<Result<User>> = authUserDataSource.register(email, password)
}

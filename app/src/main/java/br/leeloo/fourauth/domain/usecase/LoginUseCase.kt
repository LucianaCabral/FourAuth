package br.leeloo.fourauth.domain.usecase

import br.leeloo.fourauth.domain.model.User
import br.leeloo.fourauth.domain.repository.AuthUserRepository
import kotlinx.coroutines.flow.Flow

internal class LoginUseCase(private val authRepository: AuthUserRepository) {
    operator fun invoke(email: String, password: String): Flow<Result<User>> =
        authRepository.login(email, password)
}

package br.leeloo.fourauth.domain.usecase

import br.leeloo.fourauth.domain.repository.AuthUserRepository

internal class LogoutUseCase(private val authRepository: AuthUserRepository) {
    operator fun invoke() = authRepository.logout()
}

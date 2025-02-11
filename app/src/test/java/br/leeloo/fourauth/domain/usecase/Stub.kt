package br.leeloo.fourauth.domain.usecase

import br.leeloo.fourauth.domain.model.User


internal fun createTestCredentials(): Pair<String, String> {
    return "test@test.com" to "12345"
}

internal fun createTestUser(): User {
    val (email, password) = createTestCredentials()
    return User(email = email, password = password)
}
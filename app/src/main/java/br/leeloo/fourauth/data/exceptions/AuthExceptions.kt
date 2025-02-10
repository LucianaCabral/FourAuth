package br.leeloo.fourauth.data.exceptions

internal object  AuthExceptions {

    fun firebaseUserNullException(message: String = "null user"): Exception {
        return Exception(message)
    }

    fun userNotAuthenticatedException(message: String = "Usuário não autenticado"): Exception {
        return Exception(message)
    }

    fun unknownAuthenticationException(message: String = "Erro ao fazer autenticação"): Exception {
        return Exception(message)
    }
}

package br.leeloo.fourauth.domain.usecase

import br.leeloo.fourauth.domain.model.User
import br.leeloo.fourauth.domain.repository.AuthUserRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class LoginUseCaseTest {

    private lateinit var loginUseCase: LoginUseCase
    private lateinit var authRepository: AuthUserRepository

    @Before
    fun setup() {
        authRepository = mockk()
        loginUseCase = LoginUseCase(authRepository)
    }

    @Test
    fun `invoke should return success result when login is successful`() = runTest {
        // Arrange
        val (email, password) = createTestCredentials()
        val expectedUser = createTestUser()
        val expectedResult: Flow<Result<User>> = flow { emit(Result.success(expectedUser)) }

        every { authRepository.login(email, password) } returns expectedResult

        // Act
        val result = loginUseCase(email, password).toList()

        // Assert
        assertEquals(1, result.size)
        assertEquals(true, result[0].isSuccess)
        assertEquals(expectedUser, result[0].getOrNull())
    }

    @Test
    fun `invoke should return failure result when login fails`() = runTest {
        // Arrange
        val (email, password) = createTestCredentials()
        val expectedException = RuntimeException("Login failed")
        val expectedResult: Flow<Result<User>> = flow { emit(Result.failure(expectedException)) }

        every { authRepository.login(email, password) } returns expectedResult

        // Act
        val result = loginUseCase(email, password).toList()

        // Assert
        assertEquals(1, result.size)
        assertEquals(true, result[0].isFailure)
        assertEquals(expectedException, result[0].exceptionOrNull())
    }
}
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
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RegisterUseCaseTest {

    private lateinit var registerUseCase: RegisterUseCase
    private lateinit var authRepository: AuthUserRepository

    @Before
    fun setup() {
        authRepository = mockk()
        registerUseCase = RegisterUseCase(authRepository)
    }

    @Test
    fun `invoke should return success result when register is successful`() = runTest {
        // Arrange
        val (email, password) = createTestCredentials()
        val expectedUser = createTestUser()
        val expectedResult: Flow<Result<User>> = flow { emit(Result.success(expectedUser)) }

        every { authRepository.register(email, password) } returns expectedResult

        // Act
        val result = registerUseCase(email, password).toList()

        // Assert
        assertEquals(1, result.size)
        assertTrue(result[0].isSuccess)
        assertEquals(expectedUser, result[0].getOrNull())
    }

    @Test
    fun `invoke should return failure result when register fails`() = runTest {
        // Arrange
        val (email, password) = createTestCredentials()
        val expectedException = RuntimeException("Register failed")
        val expectedResult: Flow<Result<User>> = flow { emit(Result.failure(expectedException)) }

        every { authRepository.register(email, password) } returns expectedResult

        // Act
        val result = registerUseCase(email, password).toList()

        // Assert
        assertEquals(1, result.size)
        assertTrue(result[0].isFailure)
        assertEquals(expectedException, result[0].exceptionOrNull())
    }

    @Test(expected = Throwable::class)
    fun `invoke should throw exception when email is empty`() = runTest {
        // Arrange
        val email = ""
        val password = "12345"

        // Act
        registerUseCase(email, password).toList()
    }

    @Test(expected = Throwable::class)
    fun `invoke should throw exception when password is empty`() = runTest {
        // Arrange
        val email = "test@test.com"
        val password = ""

        // Act
        registerUseCase(email, password).toList()
    }
}
package br.leeloo.fourauth.domain.usecase

import br.leeloo.fourauth.domain.repository.AuthUserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class LogoutUseCaseTest {

    private lateinit var logoutUseCase: LogoutUseCase
    private lateinit var authRepository: AuthUserRepository

    @Before
    fun setup() {
        authRepository = mockk()
        logoutUseCase = LogoutUseCase(authRepository)
    }

    @Test
    fun `invoke should call logout on authRepository`() = runTest {
        // Arrange
        every { authRepository.logout() } returns Unit

        // Act
        logoutUseCase()

        // Assert
        verify(exactly = 1) { authRepository.logout() }
    }
}
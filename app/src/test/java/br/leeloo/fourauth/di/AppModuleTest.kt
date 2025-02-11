package br.leeloo.fourauth.di

import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.mock.MockProvider

class AppModuleTest : KoinTest {

    @Before
    fun setup() {
        MockProvider.register { clazz ->
            mockk(relaxed = true)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `check modules`() {
        startKoin {
            modules(AppModule.module)
        }
    }
}
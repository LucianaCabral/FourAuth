package br.leeloo.fourauth.di

import br.leeloo.fourauth.analytics.LoginAnalytics
import br.leeloo.fourauth.presentation.viewmodel.LoginViewModel
import br.leeloo.fourauth.data.mapper.AuthUserMapper
import br.leeloo.fourauth.data.provider.AuthenticationProvider
import br.leeloo.fourauth.data.provider.AuthenticationProviderImpl
import br.leeloo.fourauth.data.repository.AuthUserRepositoryImpl
import br.leeloo.fourauth.data.source.AuthUserDataSource
import br.leeloo.fourauth.data.source.AuthUserDataSourceImpl
import br.leeloo.fourauth.domain.repository.AuthUserRepository
import br.leeloo.fourauth.domain.usecase.LoginUseCase
import br.leeloo.fourauth.domain.usecase.LogoutUseCase
import br.leeloo.fourauth.domain.usecase.RegisterUseCase
import br.leeloo.fourauth.presentation.viewmodel.HomeViewModel
import br.leeloo.fourauth.presentation.viewmodel.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal object AppModule {
    val module = module {

        single { FirebaseAuth.getInstance() }

        single<AuthenticationProvider> { AuthenticationProviderImpl(get()) }

        single { AuthUserMapper }
        single<AuthUserDataSource> { AuthUserDataSourceImpl(get(), get()) }

        single<AuthUserRepository> { AuthUserRepositoryImpl(get()) }

        factory { LoginUseCase(get()) }
        factory { RegisterUseCase(get()) }
        factory { LogoutUseCase(get()) }

        viewModel { LoginViewModel(get(), LoginAnalytics(get())) }
        viewModel { RegisterViewModel(get(),LoginAnalytics(get()))  }
        viewModel { HomeViewModel(get(), LoginAnalytics(get()))}

        factory{ LoginAnalytics(get()) }
    }
}
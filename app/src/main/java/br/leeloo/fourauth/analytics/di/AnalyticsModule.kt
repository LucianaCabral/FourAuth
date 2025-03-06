package br.leeloo.fourauth.analytics.di

import br.leeloo.fourauth.analytics.AnalyticsProvider
import br.leeloo.fourauth.analytics.AnalyticsProviderImpl
import org.koin.dsl.module


internal object AnalyticsModule {
    val module = module {
        single<AnalyticsProvider> { AnalyticsProviderImpl() }
    }
}
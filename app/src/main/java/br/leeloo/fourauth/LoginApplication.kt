package br.leeloo.fourauth

import android.app.Application
import br.leeloo.fourauth.analytics.di.AnalyticsModule
import br.leeloo.fourauth.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

open class LoginApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@LoginApplication)
            modules(AppModule.module)
            modules(AnalyticsModule.module)
        }
    }
}

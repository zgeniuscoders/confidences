package cd.zgeniuscoders.confidences

import android.app.Application
import cd.zgeniuscoders.confidences.authentication.di.authenticationModule
import cd.zgeniuscoders.confidences.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(
                appModule,
                authenticationModule
            )
        }

    }
}
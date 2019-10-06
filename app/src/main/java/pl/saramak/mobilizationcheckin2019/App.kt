package pl.saramak.mobilizationcheckin2019

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import pl.saramak.mobilizationcheckin2019.participants.ParticipantsViewModel


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        startKoin {
            androidLogger()
            androidContext(this@App)
            val myModules = listOf(module {
                single<LoginService> { AlwaysFailLoginService() }
                viewModel { MainViewModel(get()) }
            },
                module {
                    viewModel { ParticipantsViewModel() }
                }
            )
            modules(myModules)
        }
    }
}
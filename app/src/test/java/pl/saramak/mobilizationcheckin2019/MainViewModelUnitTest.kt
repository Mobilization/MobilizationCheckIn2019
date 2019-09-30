package pl.saramak.mobilizationcheckin2019

import android.util.Log
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.inject
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainViewModelUnitTest : KoinTest {

    val mainViewModel: MainViewModel by inject()

    class AlwysSuccessLS : LoginService{
        override fun login(email: String, password: String): Boolean {
            return true
        }
    }

    @Before
    fun before(){
        startKoin {
            modules(listOf(module{
                single<LoginService> { AlwaysFailLoginService() }
                viewModel<MainViewModel> { MainViewModel(get()) }
            }))
        }
    }

    @Test
    fun loginTest() {
        assertEquals(false, mainViewModel.login("fsd", "fewfw"))
    }
}

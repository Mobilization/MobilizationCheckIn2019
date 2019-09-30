package pl.saramak.mobilizationcheckin2019

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import mu.KotlinLogging
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.saramak.core.ui.BaseActivity
import pl.saramak.core.ui.data.ResError
import pl.saramak.core.ui.data.Success

class MainActivity : BaseActivity() {
    private val logger = KotlinLogging.logger {}
    val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeViewModel(getViewModel())
        login_button.setOnClickListener {
            viewModel.login(login.text.toString(), password.text.toString())
        }
    }

    fun observeViewModel(viewModel: MainViewModel) {
        observe(viewModel.user) {
            when(it){
                is Success->{

                }
                is ResError->{
                    login.error = it.exception.message
                }
            }
        }
    }

}

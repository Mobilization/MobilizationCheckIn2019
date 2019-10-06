package pl.saramak.mobilizationcheckin2019.participants

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mu.KotlinLogging
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.saramak.core.ui.BaseActivity
import pl.saramak.mobilizationcheckin2019.MainViewModel
import pl.saramak.mobilizationcheckin2019.R

class ParticipantsActivity : BaseActivity() {

    private val logger = KotlinLogging.logger {}
    val viewModel: ParticipantsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participants)
    }
}

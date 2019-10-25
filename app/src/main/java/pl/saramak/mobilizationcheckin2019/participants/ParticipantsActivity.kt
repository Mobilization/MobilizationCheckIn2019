package pl.saramak.mobilizationcheckin2019.participants

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_participants.*
import mu.KotlinLogging
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.saramak.core.ui.BaseActivity
import pl.saramak.mobilizationcheckin2019.R
import pl.saramak.mobilizationcheckin2019.SearchTextListener
import pl.saramak.mobilizationcheckin2019.SimpleScannerActivity


class ParticipantsActivity : BaseActivity() {

    private val logger = KotlinLogging.logger {}
    val viewModel: ParticipantsViewModel by viewModel()
    lateinit var mAdapter: UserAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participants)
        user_list.setHasFixedSize(true)
        user_list.setLayoutManager(LinearLayoutManager(this));
        mAdapter = UserAdapter();
        user_list.setAdapter(mAdapter);
        mAdapter.setUserClickListener = { u:User ->
            viewModel.checkUser(u)
        }
        observeViewModel(viewModel)
        search_text.addTextChangedListener(SearchTextListener(mAdapter))

        bar_code_scanner.setOnClickListener {
            Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(object : PermissionListener {
                    override fun onPermissionRationaleShouldBeShown(
                        permission: PermissionRequest?,
                        token: PermissionToken?
                    ) {
                        
                    }

                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        val intent = Intent(this@ParticipantsActivity, SimpleScannerActivity::class.java)
                        startActivityForResult(intent, 0)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        //can not scan
                        bar_code_scanner.visibility = View.GONE
                    }

                }).check()
        }
    }

    private fun observeViewModel(viewModel: ParticipantsViewModel) {
        observe(viewModel.userStatus) {
            mAdapter.myDataset = it
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK) {
            data?.let {
                val t: String = it.getStringExtra("RESULT")
                search_text.setText(t)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}

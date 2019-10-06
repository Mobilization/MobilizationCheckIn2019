package pl.saramak.mobilizationcheckin2019

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import mu.KotlinLogging
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.saramak.core.ui.BaseActivity
import pl.saramak.core.ui.data.ResError
import pl.saramak.core.ui.data.Success
import pl.saramak.mobilizationcheckin2019.participants.ParticipantsActivity

class MainActivity : BaseActivity() {
    private val RC_SIGN_IN: Int = 2312

    private val logger = KotlinLogging.logger {}
    val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeViewModel(getViewModel())

        setUserInfo()
    }

    private fun setLoginButton() {
        login_button.text="Login"
        info_about_user.text = ""
        login_button.setOnClickListener {
            //            viewModel.login(login.text.toString(), password.text.toString())
            // Choose authentication providers
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build()
            )
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                RC_SIGN_IN
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                setUserInfo()
            } else {
                logger.error { "Unable to login user" }
            }
        }
    }

    private fun setUserInfo() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {u->
            setLoginUserInfo(u)
            info_about_user.postDelayed({
                startActivity(Intent(this, ParticipantsActivity::class.java))
            }, 1000)
        } ?: run {
            setLoginButton()
        }
    }

    private fun setLoginUserInfo(u: FirebaseUser) {
        logger.info { u.toString() }
        info_about_user.text = "${u.displayName} + ${u.email}"
        login_button.text = "Logout"
        login_button.setOnClickListener {
            AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    setLoginButton()
                }
        }
    }

    fun observeViewModel(viewModel: MainViewModel) {
        observe(viewModel.user) {
            when(it){
                is Success->{

                }
                is ResError->{
//                    login.error = it.exception.message
                }
            }
        }
    }

}

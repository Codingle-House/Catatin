package `in`.catat.presentation.login

import `in`.catat.R
import `in`.catat.base.BaseActivity
import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by pertadima on 15,October,2020
 */

class LoginActivity : BaseActivity(R.layout.activity_login) {
    override fun onViewCreated() {
        auth = FirebaseAuth.getInstance()
        setupToolbar()
        doGoogleLogin()
        configureGoogleSignIn()
    }

    override fun onViewModelObserver() {

    }

    private fun doGoogleLogin() {
        login_button_google.setOnClickListener {
            signIn()
        }
    }

    private fun setupToolbar() {
        login_toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}
package `in`.catat.presentation.login

import android.view.LayoutInflater
import dagger.hilt.android.AndroidEntryPoint
import `in`.catat.base.BaseActivity
import `in`.catat.databinding.ActivityLoginBinding

/**
 * Created by pertadima on 15,October,2020
 */

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityLoginBinding
        get() = ActivityLoginBinding::inflate

    override fun onViewCreated() {
        setupToolbar()
        doGoogleLogin()
        configureGoogleSignIn()
    }

    override fun onViewModelObserver() {

    }

    private fun doGoogleLogin() = binding.loginButtonGoogle.setOnClickListener {
        googleSignIn()
    }

    private fun setupToolbar() = binding.loginToolbar.setNavigationOnClickListener {
        finish()
    }
}
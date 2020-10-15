package `in`.catat.base

import `in`.catat.R
import `in`.catat.presentation.login.LoginActivity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import id.co.catatin.core.commons.CustomViewUnbinder

/**
 * Created by pertadima on 15,October,2020
 */

abstract class BaseActivity : AppCompatActivity {
    @LayoutRes
    private var contentLayoutId: Int = -1

    constructor() : super()

    constructor(@LayoutRes layoutResID: Int) : super() {
        contentLayoutId = layoutResID
    }

    private var rootView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (contentLayoutId != -1) {
            setContentView(contentLayoutId)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        }
        onViewCreated()
        onViewModelObserver()
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)
        rootView = view
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        rootView = LayoutInflater.from(this).inflate(layoutResID, null)
        setContentView(rootView)
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        super.setContentView(view, params)
        rootView = view
    }

    abstract fun onViewCreated()
    abstract fun onViewModelObserver()

    protected fun <T> LiveData<T>.onResult(action: (T) -> Unit) {
        observe(this@BaseActivity, Observer { data -> data?.let(action) })
    }

    override fun onDestroy() {
        CustomViewUnbinder.unbindDrawables(rootView)
        super.onDestroy()
    }

    fun checkIsUserLoggedIn(userLoginAction: () -> Unit) {
        //TODO: CHECK USER LOGGED IN
        val isUserLogin = false
        if (isUserLogin) {
            userLoginAction.invoke()
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}

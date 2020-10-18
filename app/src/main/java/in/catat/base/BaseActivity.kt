package `in`.catat.base

import `in`.catat.R
import `in`.catat.presentation.login.LoginActivity
import `in`.catat.util.tracking.TrackingUtil
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import id.co.catatin.core.commons.CustomViewUnbinder
import id.co.catatin.core.ext.getColorCompat
import javax.inject.Inject


/**
 * Created by pertadima on 15,October,2020
 */

abstract class BaseActivity : AppCompatActivity {
    @Inject
    lateinit var trackingUtil: TrackingUtil

    @Inject
    lateinit var auth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInOptions: GoogleSignInOptions

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
            window.statusBarColor = getColorCompat(R.color.colorPrimary)
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

    protected fun AdView.initializeAdMob() {
        MobileAds.initialize(this@BaseActivity) {

        }
        val adRequest = AdRequest.Builder().build()
        loadAd(adRequest)
    }

    override fun onDestroy() {
        CustomViewUnbinder.unbindDrawables(rootView)
        super.onDestroy()
    }

    protected fun checkIsUserLoggedIn(userLoginAction: () -> Unit) {
        //TODO: CHECK USER LOGGED IN
        val isUserLogin = false
        if (isUserLogin) {
            userLoginAction.invoke()
        } else {
            //TODO: CHANGE TO NEW ACTIVITY RESULT API
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    protected fun configureGoogleSignIn() {
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
    }

    //TODO: CHANGE TO NEW ACTIVITY RESULT API
    protected fun googleSignIn() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                finish()
            } else {
                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out)
    }

    companion object {
        private const val RC_SIGN_IN: Int = 1
    }
}

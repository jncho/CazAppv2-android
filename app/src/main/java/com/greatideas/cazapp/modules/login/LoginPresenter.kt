package com.greatideas.cazapp.modules.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.greatideas.cazapp.R
import com.greatideas.cazapp.cazAppDB
import com.greatideas.cazapp.modules.main.MainActivity
import io.realm.mongodb.Credentials


class LoginPresenter(var view: LoginContract.View) : LoginContract.Presenter {

    lateinit var router: NavController

    companion object {
        val RC_SIGN_IN = 433
    }

    override fun onViewCreated(router: NavController) {
        this.router = router
    }

    override fun onLoginGoogle(fragment: LoginFragment) {

        view.showLoading()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode(fragment.getString(R.string.server_client_id))
            .build()
        val signInIntent = GoogleSignIn.getClient(fragment.activity as MainActivity, gso).signInIntent
        fragment.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onResultLoginGoogle(requestCode: Int, data: Intent) {
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            val googleCredentials: Credentials = Credentials.google(account?.serverAuthCode)

            cazAppDB.loginAsync(googleCredentials) {
                if (it.isSuccess) {
                    Log.v(TAG, "Successfully authenticated using Google OAuth.")
                    router.navigate(R.id.toSearchFragment)
                } else {
                    Log.e(TAG, "Error logging in: ${it.error}")
                }
            }

            // Signed in successfully, show authenticated UI.
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        }

    }

    override fun onSkipLogin() {
        view.showLoading()

        cazAppDB.loginAsync(Credentials.anonymous()) {
            if (it.isSuccess) {
                Log.v(TAG, "Successfully authenticated anonymously.")
                router.navigate(R.id.toSearchFragment)
                view.hideLoading()
            } else {
                Log.e(TAG, it.error.toString())
            }
        }
    }
}
package com.greatideas.cazapp.modules.login

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController

interface LoginContract {

    interface View {
        fun showLoading()
        fun hideLoading()
    }
    interface Presenter{
        fun onViewCreated(router: NavController)
        fun onLoginGoogle(fragment: LoginFragment)
        fun onResultLoginGoogle(requestCode: Int,data: Intent)
        fun onSkipLogin()
    }
    interface Inspector{}

}
package com.greatideas.cazapp.modules.main

import androidx.navigation.NavController
import com.google.android.material.navigation.NavigationView

interface MainContract {

    interface View{
        fun hideDrawer()
        fun hideKeyboard()
    }
    interface Presenter{
        fun onViewCreated(router: NavController)
        fun onDestroy()

        fun navigationItemSelectedListener() : NavigationView.OnNavigationItemSelectedListener
        fun checkLogin()
    }
}
package com.greatideas.cazapp.modules.main

import android.util.Log
import androidx.navigation.NavController
import com.google.android.material.navigation.NavigationView
import com.greatideas.cazapp.R
import com.greatideas.cazapp.TAG
import com.greatideas.cazapp.cazAppDB
import io.realm.mongodb.User
import java.lang.IllegalStateException

class MainPresenter(private var view: MainContract.View?) : MainContract.Presenter {

    private var router: NavController? = null
    private var user: User? = null

    override fun onViewCreated(router: NavController) {
        this.router = router
    }

    override fun navigationItemSelectedListener() = NavigationView.OnNavigationItemSelectedListener {
        view?.hideKeyboard()
        when (it.itemId) {
            R.id.action_search -> {
                router?.navigate(R.id.toSearchFragment)
                view?.hideDrawer()
                true
            }
            R.id.action_shared_lists -> false
            R.id.action_custom_lists -> {
                router?.navigate(R.id.toListsFragment)
                view?.hideDrawer()
                true
            }
            R.id.action_favorites -> {
                router?.navigate(R.id.toFavoritesFragment)
                view?.hideDrawer()
                true
            }
            R.id.action_logout, R.id.action_login -> false
            else -> true
        }
    }

    override fun onDestroy() {
        view = null
    }

    override fun checkLogin() {
        try {
            user = cazAppDB.currentUser()
        } catch (e: IllegalStateException){
            Log.w(TAG(), e)
        }
        if (user == null){
            router?.navigate(R.id.toLoginFragment)
        }
    }
}
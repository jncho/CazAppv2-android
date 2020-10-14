package com.greatideas.cazapp.modules.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.greatideas.cazapp.R
import com.greatideas.cazapp.modules.main.MainActivity
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment(), LoginContract.View {

    lateinit var presenter: LoginContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = LoginPresenter(this)

        card_google.setOnClickListener { presenter.onLoginGoogle(this) }
        skip.setOnClickListener { presenter.onSkipLogin() }

        (activity as MainActivity).actionbar.hide()
        hideLoading()
    }

    override fun onResume() {
        super.onResume()
        presenter.onViewCreated(findNavController())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.onResultLoginGoogle(requestCode, data!!)
    }

    override fun showLoading() {
        loading_login.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loading_login.visibility = View.GONE
    }
}
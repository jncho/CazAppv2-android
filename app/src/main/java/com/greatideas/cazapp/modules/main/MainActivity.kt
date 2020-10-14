package com.greatideas.cazapp.modules.main

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation.findNavController
import com.greatideas.cazapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View {

    private var presenter: MainContract.Presenter? = null
    lateinit var actionbar: ActionBar
    lateinit var toolbar: Toolbar
    lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this)

        toolbar = my_toolbar
        setSupportActionBar(my_toolbar)
        my_toolbar.title = getString(R.string.title_main_view)
        actionbar = supportActionBar!!
        actionbar.show()

        setupDrawerToggle()
        setupNavigationView()
    }

    override fun onResume() {

        presenter?.onViewCreated(findNavController(nav_host_fragment))
        presenter?.checkLogin()
        super.onResume()
    }

    fun setupDrawerToggle() {
        drawerToggle = object : ActionBarDrawerToggle(
            this,
            drawer_layout as DrawerLayout,
            my_toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        ) {}
        drawerToggle.isDrawerIndicatorEnabled = true
        (drawer_layout as DrawerLayout).addDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigateUp(): Boolean {
        return super.onNavigateUp()
    }

    private fun setupNavigationView() {
        navigation_view.setNavigationItemSelectedListener(presenter?.navigationItemSelectedListener())
    }

    override fun hideDrawer() {
        drawer_layout.closeDrawers()
    }

}
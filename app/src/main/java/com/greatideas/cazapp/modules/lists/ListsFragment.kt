package com.greatideas.cazapp.modules.lists

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.greatideas.cazapp.R
import com.greatideas.cazapp.entity.CustomList
import com.greatideas.cazapp.modules.main.MainActivity
import kotlinx.android.synthetic.main.list_fragment.*
import kotlinx.android.synthetic.main.lists_fragment.*

class ListsFragment : Fragment(), ListsContract.View {

    lateinit var presenter: ListsContract.Presenter
    lateinit var adapterLists: ListsAdapter
    lateinit var snackbarMessage: Snackbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.lists_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).actionbar.title = getString(R.string.title_lists_toolbar)
        presenter = ListsPresenter(this)

        snackbarMessage = Snackbar.make(fragment_lists_view, "", Snackbar.LENGTH_LONG)
        // Recycler View
        adapterLists = ListsAdapter(presenter, null) { presenter.onListSelected(it) }
        customlists_list.apply {
            layoutManager = LinearLayoutManager(activity)
            this.adapter = adapterLists
            setHasFixedSize(true)
        }
    }

    override fun updateRecyclerView(){
        adapterLists.notifyDataSetChanged()
    }

    override fun onResume() {
        presenter.onViewCreated(findNavController())

        presenter.getLists()

        (activity as MainActivity).actionbar.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).drawerToggle.isDrawerIndicatorEnabled = true
        (activity as MainActivity).setupDrawerToggle()
        super.onResume()
    }

    override fun updateRecyclerView(customLists: List<CustomList>?) {
        adapterLists.customLists = customLists
        adapterLists.notifyDataSetChanged()
    }

    override fun showDeletedAlertDialog(title: String, message: String, callback: () -> Unit) {

        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle(title)
                setMessage(message)
                setPositiveButton("Ok") { _, _ ->
                    callback()
                }
                setNegativeButton("Cancel",null)
            }
            // Set other dialog properties


            // Create the AlertDialog
            builder.create()
        }
        alertDialog?.show()

    }

    override fun showMessage(message: String) {
        snackbarMessage.setText(message).show()
    }
}
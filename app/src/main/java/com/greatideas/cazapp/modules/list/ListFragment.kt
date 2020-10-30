package com.greatideas.cazapp.modules.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.greatideas.cazapp.R
import com.greatideas.cazapp.entity.CustomList
import com.greatideas.cazapp.modules.main.MainActivity
import kotlinx.android.synthetic.main.detail_list_song_fragment.*
import kotlinx.android.synthetic.main.list_fragment.*

class ListFragment : Fragment(), ListContract.View {

    lateinit var presenter: ListContract.Presenter
    lateinit var adapterList: ListAdapter
    private lateinit var touchHelper: ItemTouchHelper
    lateinit var snackbarMessage: Snackbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ListPresenter(this)

        snackbarMessage = Snackbar.make(fragment_list_view,"", Snackbar.LENGTH_LONG)

        // Recycler View
        adapterList = ListAdapter(presenter,null,null){ viewHolder ->
            touchHelper.startDrag(viewHolder)
        }
        customlist_list.apply {
            layoutManager = LinearLayoutManager(activity)
            this.adapter = adapterList
            setHasFixedSize(true)
            val callback = TouchHelperCallback(adapter as TouchHelperCallback.ItemTouchHelperAdapter)
            touchHelper = ItemTouchHelper(callback)
            touchHelper.attachToRecyclerView(this)
        }
    }

    override fun onResume() {
        presenter.onViewCreated(findNavController())
        presenter.getCustomList(arguments?.getString("idCustomList")!!)

        (activity as MainActivity).drawerToggle.isDrawerIndicatorEnabled = false
        (activity as MainActivity).actionbar.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).toolbar.setNavigationOnClickListener{
            presenter.backButtonClicked()
        }
        super.onResume()
    }

    override fun updateRecyclerView(customList: CustomList?) {
        if (customList != null) {
            (activity as MainActivity).actionbar.title = customList.title
            adapterList.customList = customList
            adapterList.songs = customList.songs
        }
        adapterList.notifyDataSetChanged()
    }

    override fun showMessage(message: String) {
        snackbarMessage.setText(message).show()
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

}
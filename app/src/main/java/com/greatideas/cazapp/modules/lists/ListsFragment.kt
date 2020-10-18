package com.greatideas.cazapp.modules.lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.greatideas.cazapp.R
import com.greatideas.cazapp.entity.CustomList
import com.greatideas.cazapp.modules.main.MainActivity
import kotlinx.android.synthetic.main.lists_fragment.*

class ListsFragment : Fragment(), ListsContract.View{

    lateinit var presenter: ListsContract.Presenter
    lateinit var adapterLists: ListsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.lists_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).actionbar.title = getString(R.string.title_lists_toolbar)
        presenter = ListsPresenter(this)

        // Recycler View
        adapterLists = ListsAdapter(null) { presenter.onListSelected(it) }
        customlists_list.apply {
            layoutManager = LinearLayoutManager(activity)
            this.adapter = adapterLists
            setHasFixedSize(true)
        }
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


}
package com.greatideas.cazapp.modules.list

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
import kotlinx.android.synthetic.main.list_fragment.*

class ListFragment : Fragment(), ListContract.View {

    lateinit var presenter: ListContract.Presenter
    lateinit var adapterList: ListAdapter

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

        // Recycler View
        adapterList = ListAdapter(null) { presenter.onListSongSelected(it) }
        customlist_list.apply {
            layoutManager = LinearLayoutManager(activity)
            this.adapter = adapterList
            setHasFixedSize(true)
        }
    }

    override fun onResume() {
        presenter.onViewCreated(findNavController())
        presenter.getCustomList(arguments?.getString("idCustomList")!!)

        (activity as MainActivity).actionbar.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).drawerToggle.isDrawerIndicatorEnabled = true
        (activity as MainActivity).setupDrawerToggle()
        super.onResume()
    }

    override fun updateRecyclerView(customList: CustomList) {
        (activity as MainActivity).actionbar.title = customList.title
        adapterList.songs = customList.songs
        adapterList.notifyDataSetChanged()
    }


}
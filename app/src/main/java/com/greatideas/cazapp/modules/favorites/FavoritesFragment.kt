package com.greatideas.cazapp.modules.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.greatideas.cazapp.R
import com.greatideas.cazapp.entity.FavoriteSong
import com.greatideas.cazapp.entity.SearchSong
import com.greatideas.cazapp.modules.main.MainActivity
import com.greatideas.cazapp.modules.search.ResultsSearchAdapter
import kotlinx.android.synthetic.main.favorites_fragment.*
import kotlinx.android.synthetic.main.search_fragment.*

class FavoritesFragment : Fragment(), FavoritesContract.View{

    lateinit var presenter: FavoritesContract.Presenter
    lateinit var adapter: FavoritesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.favorites_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).actionbar.title = getString(R.string.title_favorites_toolbar)
        presenter = FavoritesPresenter(this)

        // Recycler View
        adapter = FavoritesAdapter(null) { presenter.onFavoriteSongSelected(it) }
        favorite_list.layoutManager = LinearLayoutManager(activity)
        favorite_list.adapter = adapter
        favorite_list.setHasFixedSize(true)
    }

    override fun onResume() {
        presenter.onViewCreated(findNavController())

        // Update favorite list
        presenter.getFavorites()

        (activity as MainActivity).actionbar.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).drawerToggle.isDrawerIndicatorEnabled = true
        (activity as MainActivity).setupDrawerToggle()
        super.onResume()
    }

    override fun updateRecyclerView(songs: List<FavoriteSong>?) {
        adapter.songs = songs
        adapter.notifyDataSetChanged()
    }


}
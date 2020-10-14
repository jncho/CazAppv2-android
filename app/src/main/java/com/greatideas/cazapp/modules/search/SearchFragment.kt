package com.greatideas.cazapp.modules.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.greatideas.cazapp.R
import com.greatideas.cazapp.entity.SearchSong
import com.greatideas.cazapp.entity.Song
import com.greatideas.cazapp.modules.main.MainActivity
import io.realm.OrderedRealmCollection
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.search_fragment.*

class SearchFragment : Fragment(), SearchContract.View {

    lateinit var realm: Realm
    lateinit var presenter: SearchContract.Presenter
    lateinit var adapter: ResultsSearchAdapter
    lateinit var snackbarMessage: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        results_search_view.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        results_search_view.setIconifiedByDefault(false) // Do not iconify the widget; expand it by default
        (activity as MainActivity).actionbar.title = "Search Songs"
        (activity as MainActivity).actionbar.show()

        presenter = SearchPresenter(this)

        // Recycler view
        adapter = ResultsSearchAdapter(null) { presenter.onSongSelected(it) }
        result_search_list.layoutManager = LinearLayoutManager(activity)
        result_search_list.adapter = adapter
        result_search_list.setHasFixedSize(true)
        //result_search_list.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))


        // Actions Listeners
        results_search_view.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.onQueryTextSubmit(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        // Views widgets
        snackbarMessage = Snackbar.make(fragment_songs_view,"",Snackbar.LENGTH_LONG)

    }


    override fun onResume() {
        super.onResume()
        (activity as MainActivity).actionbar.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).drawerToggle.isDrawerIndicatorEnabled = true
        (activity as MainActivity).setupDrawerToggle()

        presenter.onViewCreated(findNavController())
    }

    override fun showMessage(message: String) {
        snackbarMessage.setText(message).show()
    }

    override fun updateRecyclerView(songs: List<SearchSong>?) {
        adapter.songs = songs
        adapter.notifyDataSetChanged()
    }

}
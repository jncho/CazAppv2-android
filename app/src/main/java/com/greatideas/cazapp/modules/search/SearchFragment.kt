package com.greatideas.cazapp.modules.search

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private val viewModel: BatchViewModel by viewModels()
    private var songs: List<SearchSong>? = null

    companion object {
        const val LIST_STATE_KEY = "recycler_list_state"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        result_search_list.layoutManager = LinearLayoutManager(activity)
        result_search_list.adapter = adapter
        result_search_list.setHasFixedSize(true)
        //result_search_list.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))


        // Actions Listeners
        results_search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.onQueryTextSubmit(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        // Views widgets
        snackbarMessage = Snackbar.make(fragment_songs_view, "", Snackbar.LENGTH_LONG)

    }


    override fun onResume() {
        super.onResume()
        (activity as MainActivity).actionbar.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).drawerToggle.isDrawerIndicatorEnabled = true
        (activity as MainActivity).setupDrawerToggle()

        presenter.onViewCreated(findNavController())

        if (viewModel.listState != null) {
            result_search_list.layoutManager?.onRestoreInstanceState(viewModel.listState)
            (result_search_list.adapter as ResultsSearchAdapter).songs = this.songs
            (result_search_list.adapter as ResultsSearchAdapter).notifyDataSetChanged()
        }
    }

    override fun showMessage(message: String) {
        snackbarMessage.setText(message).show()
    }

    override fun updateRecyclerView(songs: List<SearchSong>?) {
        this.songs = songs
        adapter.songs = songs
        adapter.notifyDataSetChanged()
    }

    override fun hideKeyboard() {
        val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity?.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun showLoading(show: Boolean) {
        if (show) {
            loading_bar_songs.visibility = View.VISIBLE
            result_search_list.visibility = View.GONE
        } else {
            loading_bar_songs.visibility = View.GONE
            result_search_list.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.listState = result_search_list.layoutManager?.onSaveInstanceState()
        viewModel.songs = this.songs
    }
}
package com.greatideas.cazapp.modules.search

import androidx.navigation.NavController
import com.greatideas.cazapp.entity.SearchSong
import com.greatideas.cazapp.entity.Song
import io.realm.OrderedRealmCollection
import io.realm.Realm

interface SearchContract {
    interface View{
        fun updateRecyclerView(songs: List<SearchSong>?)
        fun showMessage(message: String)
    }
    interface Presenter{
        fun onViewCreated(router: NavController)
        fun onDestroy()
        fun onSongSelected(song: SearchSong)
        fun onQueryTextSubmit(query: String)
    }
    interface Interactor {
        fun getSongs(query: String,callback: ResultCallback<List<SearchSong>>)
    }

    interface ResultCallback<T>{
        fun onSuccessExecute(result: T)
        fun onFailureExecute(description: String?)
    }
}
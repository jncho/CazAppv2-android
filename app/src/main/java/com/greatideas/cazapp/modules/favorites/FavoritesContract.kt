package com.greatideas.cazapp.modules.favorites

import androidx.navigation.NavController
import com.greatideas.cazapp.entity.FavoriteSong
import com.greatideas.cazapp.entity.ListSong

interface FavoritesContract {
    interface View{
        fun updateRecyclerView(songs: List<FavoriteSong>?)
        fun showMessage(message: String)
        fun showDeletedAlertDialog(title: String, message: String, callback: () -> Unit)
    }
    interface Presenter{
        fun onViewCreated(router: NavController)
        fun onDestroy()
        fun onFavoriteSongSelected(favoriteSong: FavoriteSong)
        fun getFavorites()
        fun onDeleteSong(favoriteSong: FavoriteSong)
    }
    interface Interactor {
        fun getFavorites(listener: (List<FavoriteSong>) -> Unit)
        fun deleteSong(favoriteSong: FavoriteSong, callback: () -> Unit)
    }
}
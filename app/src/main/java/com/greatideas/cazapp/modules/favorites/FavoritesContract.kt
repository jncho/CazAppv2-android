package com.greatideas.cazapp.modules.favorites

import androidx.navigation.NavController
import com.greatideas.cazapp.entity.FavoriteSong

interface FavoritesContract {
    interface View{
        fun updateRecyclerView(songs: List<FavoriteSong>?)
    }
    interface Presenter{
        fun onViewCreated(router: NavController)
        fun onDestroy()
        fun onFavoriteSongSelected(favoriteSong: FavoriteSong)
        fun getFavorites()
    }
    interface Interactor {
        fun getFavorites(listener: (List<FavoriteSong>) -> Unit)
    }
}
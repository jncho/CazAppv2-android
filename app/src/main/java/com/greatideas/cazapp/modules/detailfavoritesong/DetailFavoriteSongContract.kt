package com.greatideas.cazapp.modules.detailfavoritesong

import androidx.navigation.NavController
import com.greatideas.cazapp.entity.FavoriteSong
import com.greatideas.cazapp.entity.Song

interface DetailFavoriteSongContract {
    interface View{
        fun updateView(song: FavoriteSong)
        fun showMessage(message: String)
    }
    interface Presenter{
        fun onViewCreated(router: NavController)
        fun getSong(idSong: String)
        fun onDestroy()
        fun backButtonClicked()
        fun onActionDownSemitone(favoriteSong: FavoriteSong)
        fun onActionUpSemitone(favoriteSong: FavoriteSong)
    }
    interface Interactor{
        fun getSong(idSong: String,callback: ResultCallbackDetailSong)
    }

    interface ResultCallbackDetailSong{
        fun onSuccessExecute(result: FavoriteSong)
        fun onFailureExecute(description: String)
    }


}
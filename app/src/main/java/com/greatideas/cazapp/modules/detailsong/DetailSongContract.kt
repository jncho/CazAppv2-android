package com.greatideas.cazapp.modules.detailsong

import androidx.navigation.NavController
import com.greatideas.cazapp.entity.FavoriteSong
import com.greatideas.cazapp.entity.Song

interface DetailSongContract {
    interface View{
        fun updateView(song: Song)
        fun showMessage(message: String)
    }
    interface Presenter{
        fun onViewCreated(router: NavController)
        fun getSong(idSong: String)
        fun onDestroy()
        fun backButtonClicked()
        fun addFavorite(song: Song, tune: Int, fontSize: Float)
    }
    interface Interactor{
        fun getSong(idSong: String,callback: ResultCallbackDetailSong)
        fun addFavorite(song: Song, tune: Int, fontSize: Float, callback: (isSuccess: Boolean,error: String?) -> Unit)
    }

    interface ResultCallbackDetailSong{
        fun onSuccessExecute(result: Song)
        fun onFailureExecute(description: String)
    }


}
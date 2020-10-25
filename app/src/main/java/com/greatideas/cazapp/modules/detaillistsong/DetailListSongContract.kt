package com.greatideas.cazapp.modules.detailfavoritesong

import androidx.navigation.NavController
import com.greatideas.cazapp.entity.FavoriteSong
import com.greatideas.cazapp.entity.ListSong

interface DetailListSongContract {
    interface View{
        fun updateView(listSong: ListSong)
        fun showMessage(message: String)
    }
    interface Presenter{
        fun onViewCreated(router: NavController)
        fun getListSong(idCustomList: String,idListSong: String)
        fun onDestroy()
        fun backButtonClicked()
    }
    interface Interactor{
        fun getListSong(idCustomList: String,idListSong: String,callback: ResultCallbackDetailListSong)
    }

    interface ResultCallbackDetailListSong{
        fun onSuccessExecute(result: ListSong)
        fun onFailureExecute(description: String)
    }


}
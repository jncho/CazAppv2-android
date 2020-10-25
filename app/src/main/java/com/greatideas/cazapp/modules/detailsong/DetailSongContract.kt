package com.greatideas.cazapp.modules.detailsong

import android.view.MenuItem
import androidx.navigation.NavController
import com.google.android.gms.common.util.Strings
import com.greatideas.cazapp.entity.CustomList
import com.greatideas.cazapp.entity.FavoriteSong
import com.greatideas.cazapp.entity.Song
import org.bson.types.ObjectId

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
        fun getTitlesCustomLists() : Map<ObjectId,String>?
        fun onCustomListSelect(idCustomList: ObjectId,song: Song,tune: Int, fontSize: Float)
        fun onCreateNewList(titleList: String)
        fun onActionDownSemitone(song: Song)
        fun onActionUpSemitone(song: Song)

    }
    interface Interactor{
        fun getSong(idSong: String,callback: ResultCallbackDetailSong)
        fun addFavorite(song: Song, tune: Int, fontSize: Float, callback: (isSuccess: Boolean,error: String?) -> Unit)
        fun getCustomLists() : List<CustomList>?
        fun addSongToCustomList(idCustomList: ObjectId, song: Song, tune: Int, fontSize: Float,callback: (isSuccess: Boolean,error: String?) -> Unit)
        fun createCustomList(titleList: String) : Boolean
    }

    interface ResultCallbackDetailSong{
        fun onSuccessExecute(result: Song)
        fun onFailureExecute(description: String)
    }


}
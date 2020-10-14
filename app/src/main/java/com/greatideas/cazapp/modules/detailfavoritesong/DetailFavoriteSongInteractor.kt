package com.greatideas.cazapp.modules.detailfavoritesong

import android.content.ContentValues
import android.util.Log
import com.greatideas.cazapp.cazAppDB
import com.greatideas.cazapp.entity.FavoriteSong
import com.greatideas.cazapp.entity.Song
import com.greatideas.cazapp.entity.SongDecoder
import com.greatideas.cazapp.localRealm
import io.realm.exceptions.RealmPrimaryKeyConstraintException
import io.realm.kotlin.where
import io.realm.mongodb.functions.Functions
import org.bson.types.ObjectId

class DetailFavoriteSongInteractor() : DetailFavoriteSongContract.Interactor {

    override fun getSong(idSong: String, callback: DetailFavoriteSongContract.ResultCallbackDetailSong) {
        localRealm.executeTransaction{
            try {
                val favoriteSong = it.where<FavoriteSong>().equalTo("id", ObjectId( idSong)).findFirst()!!
                callback.onSuccessExecute(favoriteSong)
            }catch (e: Exception){
                callback.onFailureExecute("Error to get favorite song from local realm database")
            }
        }
    }
}
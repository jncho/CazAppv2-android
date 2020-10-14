package com.greatideas.cazapp.modules.detailsong

import android.content.ContentValues
import android.util.Log
import com.greatideas.cazapp.cazAppDB
import com.greatideas.cazapp.entity.FavoriteSong
import com.greatideas.cazapp.entity.Song
import com.greatideas.cazapp.entity.SongDecoder
import com.greatideas.cazapp.localRealm
import io.realm.exceptions.RealmPrimaryKeyConstraintException
import io.realm.kotlin.createObject
import io.realm.mongodb.functions.Functions
import java.lang.Exception

class DetailSongInteractor() : DetailSongContract.Interactor {

    override fun getSong(idSong: String, callback: DetailSongContract.ResultCallbackDetailSong) {
        val functionsManager: Functions = cazAppDB.getFunctions(cazAppDB.currentUser())
        functionsManager.callFunctionAsync("getSong", listOf(idSong), SongDecoder(), {
            if (it.isSuccess) {
                Log.v(ContentValues.TAG, "Get song success")
                callback.onSuccessExecute(it.get())
            } else {
                Log.e(ContentValues.TAG, "Error get song: ${it.error.errorMessage}")
                callback.onFailureExecute(it.error.message!!)
            }
        })
    }

    override fun addFavorite(song: Song, tune: Int, fontSize: Float, callback: (isSuccess: Boolean, error: String?) -> Unit) {
        try {
            localRealm.executeTransaction {

                it.insert(FavoriteSong(song._id,song,tune,fontSize,song.title))

            }

            callback(true,null)
        }catch (e: RealmPrimaryKeyConstraintException){
            callback(false,"Song is already in favorites")
        }
    }
}
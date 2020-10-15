package com.greatideas.cazapp.modules.detailsong

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.greatideas.cazapp.cazAppDB
import com.greatideas.cazapp.entity.*
import com.greatideas.cazapp.localRealm
import io.realm.RealmList
import io.realm.exceptions.RealmPrimaryKeyConstraintException
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import io.realm.mongodb.functions.Functions
import org.bson.types.ObjectId
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

    override fun addFavorite(
        song: Song,
        tune: Int,
        fontSize: Float,
        callback: (isSuccess: Boolean, error: String?) -> Unit
    ) {
        try {
            localRealm.executeTransaction {

                it.insert(FavoriteSong(song._id, song, tune, fontSize, song.title))

            }

            callback(true, null)
        } catch (e: RealmPrimaryKeyConstraintException) {
            callback(false, "Song is already in favorites")
        }
    }

    override fun addSongToCustomList(
        idCustomList: ObjectId,
        song: Song,
        tune: Int, fontSize: Float,
        callback: (isSuccess: Boolean, error: String?) -> Unit
    ) {
        val customList = localRealm.where<CustomList>().equalTo("id", idCustomList).findFirst()
        try {
            localRealm.executeTransaction {
                customList?.songs?.add(ListSong(song._id, song, 0, 0f, ""))
            }
            callback(true, null)
        } catch (e: Exception){
            callback(false, "Error while adding song to list.")
        }
    }

    override fun getCustomLists(): List<CustomList>? {
        return localRealm.where<CustomList>().findAll()
    }

    override fun createCustomList(titleList: String): Boolean {
        try {
            localRealm.executeTransaction {
                it.createObject<CustomList>(ObjectId()).apply {
                    this.title = titleList
                    this.songs = RealmList()
                }
            }
        }catch (e: Exception){
            Log.e(TAG,e.message!!)
            return false
        }

        return true
    }
}
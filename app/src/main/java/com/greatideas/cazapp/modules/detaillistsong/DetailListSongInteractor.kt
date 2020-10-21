package com.greatideas.cazapp.modules.detailfavoritesong

import android.content.ContentValues
import android.util.Log
import com.greatideas.cazapp.cazAppDB
import com.greatideas.cazapp.entity.*
import com.greatideas.cazapp.localRealm
import io.realm.exceptions.RealmPrimaryKeyConstraintException
import io.realm.kotlin.where
import io.realm.mongodb.functions.Functions
import org.bson.types.ObjectId

class DetailListSongInteractor() : DetailListSongContract.Interactor {

    override fun getListSong(idCustomList: String,idListSong: String, callback: DetailListSongContract.ResultCallbackDetailListSong) {
        localRealm.executeTransaction{
            try {
                val customList = it.where<CustomList>().equalTo("id", ObjectId( idCustomList)).findFirst()!!
                callback.onSuccessExecute(customList.songs.first { it.id == ObjectId(idListSong) }!!)
            }catch (e: Exception){
                callback.onFailureExecute("Error to get list song from local realm database")
            }
        }
    }
}
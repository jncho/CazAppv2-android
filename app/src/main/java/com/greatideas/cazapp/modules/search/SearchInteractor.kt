package com.greatideas.cazapp.modules.search

import android.content.ContentValues
import android.util.Log
import com.greatideas.cazapp.cazAppDB
import com.greatideas.cazapp.entity.SearchSong
import com.greatideas.cazapp.entity.SearchSongsDecoder
import com.greatideas.cazapp.entity.Song
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.mongodb.functions.Functions
import io.realm.mongodb.sync.SyncConfiguration

class SearchInteractor : SearchContract.Interactor {

    override fun getSongs(query: String, callback: SearchContract.ResultCallback<List<SearchSong>>) {
        //val functionsManager: Functions = cazAppDB.getFunctions(cazAppDB.currentUser())
        cazAppDB.currentUser()!!.functions.callFunctionAsync("searchSongsIndex", listOf(query), SearchSongsDecoder(), {
            if (it.isSuccess) {
                Log.v(ContentValues.TAG, "Get songs success")
                callback.onSuccessExecute(it.get())
            } else {
                Log.e(ContentValues.TAG, "Error get songs: ${it.error.errorMessage}")
                callback.onFailureExecute(it.error.errorMessage)
            }
        })
    }

}
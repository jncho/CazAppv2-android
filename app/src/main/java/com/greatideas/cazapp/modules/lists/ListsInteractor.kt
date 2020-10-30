package com.greatideas.cazapp.modules.lists

import com.greatideas.cazapp.entity.CustomList
import com.greatideas.cazapp.entity.ListSong
import com.greatideas.cazapp.localRealm
import io.realm.RealmObject.deleteFromRealm
import io.realm.kotlin.where

class ListsInteractor : ListsContract.Interactor {

    override fun getLists(listener: (List<CustomList>) -> Unit) {
        listener(localRealm.where<CustomList>().findAll())
    }

    override fun deleteSong(customList: CustomList, callback: () -> Unit) {
        localRealm.executeTransaction {
            customList.deleteFromRealm()
            callback()
        }
    }

}
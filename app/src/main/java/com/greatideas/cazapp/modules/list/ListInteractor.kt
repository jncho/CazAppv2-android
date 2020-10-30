package com.greatideas.cazapp.modules.list

import com.greatideas.cazapp.entity.CustomList
import com.greatideas.cazapp.entity.FavoriteSong
import com.greatideas.cazapp.entity.ListSong
import com.greatideas.cazapp.localRealm
import io.realm.kotlin.where
import org.bson.types.ObjectId

class ListInteractor : ListContract.Interactor {

    override fun getCustomList(idList: String,listener: (CustomList) -> Unit) {
        val customList = localRealm.where<CustomList>().equalTo("id", ObjectId( idList)).findFirst()!!
        listener(customList)
    }

    override fun deleteSong(listSong: ListSong, callback: () -> Unit) {
        localRealm.executeTransaction {
            listSong.deleteFromRealm()
            callback()
        }
    }

}
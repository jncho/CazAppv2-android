package com.greatideas.cazapp.modules.lists

import com.greatideas.cazapp.entity.CustomList
import com.greatideas.cazapp.localRealm
import io.realm.kotlin.where

class ListsInteractor : ListsContract.Interactor {

    override fun getLists(listener: (List<CustomList>) -> Unit) {
        listener(localRealm.where<CustomList>().findAll())
    }

}
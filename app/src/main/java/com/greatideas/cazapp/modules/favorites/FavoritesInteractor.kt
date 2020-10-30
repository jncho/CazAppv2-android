package com.greatideas.cazapp.modules.favorites

import com.greatideas.cazapp.entity.FavoriteSong
import com.greatideas.cazapp.entity.ListSong
import com.greatideas.cazapp.localRealm
import io.realm.kotlin.where

class FavoritesInteractor : FavoritesContract.Interactor {

    override fun getFavorites(listener: (List<FavoriteSong>) -> Unit) {
        listener(localRealm.where<FavoriteSong>().findAll())
    }

    override fun deleteSong(favoriteSong: FavoriteSong, callback: () -> Unit) {
        localRealm.executeTransaction {
            favoriteSong.deleteFromRealm()
            callback()
        }
    }

}
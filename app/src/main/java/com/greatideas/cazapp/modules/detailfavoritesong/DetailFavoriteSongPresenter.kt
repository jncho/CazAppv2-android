package com.greatideas.cazapp.modules.detailfavoritesong

import androidx.navigation.NavController
import com.greatideas.cazapp.entity.FavoriteSong
import com.greatideas.cazapp.entity.Song
import com.greatideas.cazapp.localRealm

class DetailFavoriteSongPresenter(var view: DetailFavoriteSongContract.View?) : DetailFavoriteSongContract.Presenter {

    var router: NavController? = null
    var interactor = DetailFavoriteSongInteractor()

    override fun onViewCreated(router: NavController) {
        this.router = router
    }

    override fun onDestroy() {
        view = null
    }

    override fun backButtonClicked() {
        router?.navigateUp()
    }

    override fun getSong(idSong: String) {

        val callback = object : DetailFavoriteSongContract.ResultCallbackDetailSong {

            override fun onFailureExecute(description: String) {
                view?.showMessage(description)
            }

            override fun onSuccessExecute(result: FavoriteSong) {
                view?.updateView(result)
            }
        }
        interactor.getSong(idSong, callback)
    }

    override fun onActionDownSemitone(favoriteSong: FavoriteSong) {
        localRealm.executeTransaction {
            favoriteSong.song?.transpose(-1)
        }
        view?.updateView(favoriteSong)
    }

    override fun onActionUpSemitone(favoriteSong: FavoriteSong) {
        localRealm.executeTransaction {
            favoriteSong.song?.transpose(+1)
        }
        view?.updateView(favoriteSong)
    }
}
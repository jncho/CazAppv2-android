package com.greatideas.cazapp.modules.detailfavoritesong

import androidx.navigation.NavController
import com.greatideas.cazapp.entity.FavoriteSong
import com.greatideas.cazapp.entity.Song

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


}
package com.greatideas.cazapp.modules.detailfavoritesong

import androidx.navigation.NavController
import com.greatideas.cazapp.entity.FavoriteSong
import com.greatideas.cazapp.entity.ListSong

class DetailListSongPresenter(var view: DetailListSongContract.View?) : DetailListSongContract.Presenter {

    var router: NavController? = null
    var interactor = DetailListSongInteractor()

    override fun onViewCreated(router: NavController) {
        this.router = router
    }

    override fun onDestroy() {
        view = null
    }

    override fun backButtonClicked() {
        router?.navigateUp()
    }

    override fun getListSong(idCustomList: String,idListSong: String) {

        val callback = object : DetailListSongContract.ResultCallbackDetailListSong {

            override fun onFailureExecute(description: String) {
                view?.showMessage(description)
            }

            override fun onSuccessExecute(result: ListSong) {
                view?.updateView(result)
            }
        }
        interactor.getListSong(idCustomList,idListSong, callback)
    }


}
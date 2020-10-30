package com.greatideas.cazapp.modules.detailsong

import androidx.navigation.NavController
import com.greatideas.cazapp.entity.FavoriteSong
import com.greatideas.cazapp.entity.Song
import org.bson.types.ObjectId

class DetailSongPresenter(var view: DetailSongContract.View?) : DetailSongContract.Presenter {

    var router: NavController? = null
    var interactor = DetailSongInteractor()

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

        val callback = object : DetailSongContract.ResultCallbackDetailSong {

            override fun onFailureExecute(description: String) {
                view?.showMessage(description)
            }

            override fun onSuccessExecute(result: Song) {
                view?.updateView(result)
            }
        }
        interactor.getSong(idSong, callback)
    }

    override fun addFavorite(song: Song, tune: Int, fontSize: Float) {

        interactor.addFavorite(song, tune, fontSize){ isSuccess, error ->
            if (isSuccess){
                view?.showMessage("Song save to favorites")
            }else{
                view?.showMessage(error!!)
            }
        }
    }

    override fun onCreateNewList(titleList: String) {
        val isSuccess = interactor.createCustomList(titleList)
        if (isSuccess){
            view?.showMessage("List created successfully")
        }else{
            view?.showMessage("Error to create list")
        }
    }

    override fun getTitlesCustomLists(): Map<ObjectId,String>?{
        return interactor.getCustomLists()?.map { it.id!! to it.title }?.toMap()
    }

    override fun onCustomListSelect(idCustomList: ObjectId, song: Song,tune: Int, fontSize: Float,) {
        interactor.addSongToCustomList(idCustomList,song,tune,fontSize){ isSuccess: Boolean, error: String? ->
            if (isSuccess){
                view?.showMessage("Song save in custom list")
            }else{
                view?.showMessage(error!!)
            }
        }
    }

    override fun onActionDownSemitone(song: Song) {
        song.transpose(-1)
        view?.updateView(song)
    }

    override fun onActionUpSemitone(song: Song) {
        song.transpose(+1)
        view?.updateView(song)
    }
}
package com.greatideas.cazapp.modules.list

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.greatideas.cazapp.R
import com.greatideas.cazapp.entity.CustomList
import com.greatideas.cazapp.entity.ListSong
import com.greatideas.cazapp.localRealm
import java.util.*

class ListPresenter(var view: ListContract.View?) : ListContract.Presenter {

    lateinit var router: NavController
    lateinit var interactor: ListContract.Interactor

    override fun onViewCreated(router: NavController) {
        this.router = router
        interactor = ListInteractor()
    }

    override fun onListSongSelected(customList: CustomList,listSong: ListSong) {
        val bundle = bundleOf("idCustomList" to customList.id?.toHexString(),"idListSong" to listSong.id?.toHexString())
        router.navigate(R.id.action_listFragment_to_detailListSongFragment,bundle)
    }

    override fun backButtonClicked() {
        router.navigateUp()
    }

    override fun onListSongMoved(songs: MutableList<ListSong>, fromPosition: Int, toPosition: Int) {
        localRealm.executeTransaction {
            Collections.swap(songs,fromPosition,toPosition)
        }
    }

    override fun getCustomList(idList: String) {
        interactor.getCustomList(idList) {
            view?.updateRecyclerView(it)
        }
    }

    override fun onDeleteSong(listSong: ListSong){
        view?.showDeletedAlertDialog("Delete song","Are you sure to delete this song?") {
            interactor.deleteSong(listSong) {
                view?.updateRecyclerView(null)
                view?.showMessage("Song deleted.")
            }
        }
    }

    override fun onDestroy() {
        view = null
    }
}
package com.greatideas.cazapp.modules.list

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.greatideas.cazapp.R
import com.greatideas.cazapp.entity.CustomList
import com.greatideas.cazapp.entity.ListSong

class ListPresenter(var view: ListContract.View?) : ListContract.Presenter {

    lateinit var router: NavController
    lateinit var interactor: ListContract.Interactor

    override fun onViewCreated(router: NavController) {
        this.router = router
        interactor = ListInteractor()
    }

    override fun onListSongSelected(listSong: ListSong) {
        val bundle = bundleOf("idList" to listSong.id?.toHexString())
        //router.navigate(R.id.action_favoritesFragment_to_detailFavoriteSongFragment,bundle)
    }

    override fun getCustomList(idList: String) {
        interactor.getCustomList(idList) {
            view?.updateRecyclerView(it)
        }
    }

    override fun onDestroy() {
        view = null
    }
}
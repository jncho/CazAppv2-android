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

    override fun onListSongSelected(customList: CustomList,listSong: ListSong) {
        val bundle = bundleOf("idCustomList" to customList.id?.toHexString(),"idListSong" to listSong.id?.toHexString())
        router.navigate(R.id.action_listFragment_to_detailListSongFragment,bundle)
    }

    override fun backButtonClicked() {
        router?.navigateUp()
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
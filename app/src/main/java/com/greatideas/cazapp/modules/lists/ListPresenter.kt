package com.greatideas.cazapp.modules.lists

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.greatideas.cazapp.R
import com.greatideas.cazapp.entity.CustomList

class ListPresenter(var view: ListContract.View?) : ListContract.Presenter {

    lateinit var router: NavController
    lateinit var interactor: ListContract.Interactor

    override fun onViewCreated(router: NavController) {
        this.router = router
        interactor = ListInteractor()
    }

    override fun onListSelected(customList: CustomList) {
        val bundle = bundleOf("idList" to customList.id?.toHexString())
        //router.navigate(R.id.action_favoritesFragment_to_detailFavoriteSongFragment,bundle)
    }

    override fun getLists() {
        interactor.getLists {
            view?.updateRecyclerView(it)
        }
    }

    override fun onDestroy() {
        view = null
    }
}
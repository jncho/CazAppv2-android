package com.greatideas.cazapp.modules.lists

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.greatideas.cazapp.R
import com.greatideas.cazapp.entity.CustomList
import com.greatideas.cazapp.entity.ListSong

class ListsPresenter(var view: ListsContract.View?) : ListsContract.Presenter {

    lateinit var router: NavController
    lateinit var interactor: ListsContract.Interactor

    override fun onViewCreated(router: NavController) {
        this.router = router
        interactor = ListsInteractor()
    }

    override fun onListSelected(customList: CustomList) {
        val bundle = bundleOf("idCustomList" to customList.id?.toHexString())
        router.navigate(R.id.action_listsFragment_to_listFragment,bundle)
    }

    override fun getLists() {
        interactor.getLists {
            view?.updateRecyclerView(it)
        }
    }

    override fun onDeleteSong(customList: CustomList){
        view?.showDeletedAlertDialog("Delete list","Are you sure to delete this list?") {
            interactor.deleteSong(customList) {
                view?.updateRecyclerView()
                view?.showMessage("List deleted.")
            }
        }
    }

    override fun onDestroy() {
        view = null
    }
}
package com.greatideas.cazapp.modules.search

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.greatideas.cazapp.R
import com.greatideas.cazapp.entity.SearchSong

class SearchPresenter(var view: SearchContract.View?) : SearchContract.Presenter {

    private var router: NavController? = null
    private var interactor = SearchInteractor()

    override fun onViewCreated(router: NavController) {
        this.router = router
    }

    override fun onDestroy() {
        view = null
    }

    override fun onSongSelected(song: SearchSong) {
        val bundle = bundleOf("idSearchSong" to song.id.toHexString())
        router?.navigate(R.id.action_searchFragment_to_detailSongFragment,bundle)
    }

    override fun onQueryTextSubmit(query: String) {
        view?.hideKeyboard()
        view?.showLoading(true)
        val callback = object:SearchContract.ResultCallback<List<SearchSong>>{
            override fun onFailureExecute(description: String?) {
                view?.showLoading(false)
                view?.showMessage(description ?: "Error")
            }

            override fun onSuccessExecute(result: List<SearchSong>) {
                view?.showLoading(false)
                view?.updateRecyclerView(result)
            }
        }
        interactor.getSongs(query,callback)

    }
}
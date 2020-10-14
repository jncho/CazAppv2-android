package com.greatideas.cazapp.modules.favorites

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.greatideas.cazapp.R
import com.greatideas.cazapp.entity.FavoriteSong

class FavoritesPresenter(var view: FavoritesContract.View?) : FavoritesContract.Presenter {

    lateinit var router: NavController
    lateinit var interactor: FavoritesContract.Interactor

    override fun onViewCreated(router: NavController) {
        this.router = router
        interactor = FavoritesInteractor()
    }

    override fun onFavoriteSongSelected(favoriteSong: FavoriteSong) {
        val bundle = bundleOf("idSearchSong" to favoriteSong.id?.toHexString())
        router.navigate(R.id.action_favoritesFragment_to_detailFavoriteSongFragment,bundle)
    }

    override fun getFavorites() {
        interactor.getFavorites {
            view?.updateRecyclerView(it)
        }
    }

    override fun onDestroy() {
        view = null
    }
}
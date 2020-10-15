package com.greatideas.cazapp.modules.lists

import androidx.navigation.NavController
import com.greatideas.cazapp.entity.*

interface ListContract {
    interface View{
        fun updateRecyclerView(songs: List<CustomList>?)
    }
    interface Presenter{
        fun onViewCreated(router: NavController)
        fun onDestroy()
        fun onListSelected(favoriteSong: CustomList)
        fun getLists()
    }
    interface Interactor {
        fun getLists(listener: (List<CustomList>) -> Unit)
    }
}
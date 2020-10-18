package com.greatideas.cazapp.modules.list

import androidx.navigation.NavController
import com.greatideas.cazapp.entity.*

interface ListContract {
    interface View{
        fun updateRecyclerView(customList: CustomList)
    }
    interface Presenter{
        fun onViewCreated(router: NavController)
        fun onDestroy()
        fun onListSongSelected(listSong: ListSong)
        fun getCustomList(idList: String)
    }
    interface Interactor {
        fun getCustomList(idList: String,listener: (CustomList) -> Unit)
    }
}
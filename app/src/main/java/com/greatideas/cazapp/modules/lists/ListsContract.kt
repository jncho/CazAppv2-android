package com.greatideas.cazapp.modules.lists

import androidx.navigation.NavController
import com.greatideas.cazapp.entity.*

interface ListsContract {
    interface View{
        fun updateRecyclerView()
        fun updateRecyclerView(customLists: List<CustomList>?)
        fun showMessage(message: String)
        fun showDeletedAlertDialog(title: String, message: String, callback: () -> Unit)
    }
    interface Presenter{
        fun onViewCreated(router: NavController)
        fun onDestroy()
        fun onListSelected(customList: CustomList)
        fun getLists()
        fun onDeleteSong(customList: CustomList)
    }
    interface Interactor {
        fun getLists(listener: (List<CustomList>) -> Unit)
        fun deleteSong(customList: CustomList, callback: () -> Unit)
    }
}
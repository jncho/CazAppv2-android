package com.greatideas.cazapp.modules.lists

import androidx.navigation.NavController
import com.greatideas.cazapp.entity.*

interface ListsContract {
    interface View{
        fun updateRecyclerView(customLists: List<CustomList>?)
    }
    interface Presenter{
        fun onViewCreated(router: NavController)
        fun onDestroy()
        fun onListSelected(customList: CustomList)
        fun getLists()
    }
    interface Interactor {
        fun getLists(listener: (List<CustomList>) -> Unit)
    }
}
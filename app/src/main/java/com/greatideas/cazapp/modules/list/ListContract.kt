package com.greatideas.cazapp.modules.list

import androidx.navigation.NavController
import com.greatideas.cazapp.entity.CustomList
import com.greatideas.cazapp.entity.ListSong
import org.apache.poi.xwpf.usermodel.XWPFDocument

interface ListContract {
    interface View{
        fun updateRecyclerView(customList: CustomList?)
        fun showMessage(message: String)
        fun showDeletedAlertDialog(title: String, message: String, callback: () -> Unit)
        fun shareDocx(docx: XWPFDocument, listName: String)
    }
    interface Presenter{
        fun onViewCreated(router: NavController)
        fun onDestroy()
        fun onListSongSelected(customList: CustomList,listSong: ListSong)
        fun getCustomList(idList: String)
        fun backButtonClicked()
        fun onListSongMoved(songs: MutableList<ListSong>,fromPosition: Int, toPosition: Int)
        fun onDeleteSong(listSong: ListSong)
        fun exportToDocx(idList: String)
    }
    interface Interactor {
        fun getCustomList(idList: String,listener: (CustomList) -> Unit)
        fun deleteSong(listSong: ListSong, callback: () -> Unit)
        fun exportToDocx (idList: String, callback: (XWPFDocument, String) -> Unit)
    }
}
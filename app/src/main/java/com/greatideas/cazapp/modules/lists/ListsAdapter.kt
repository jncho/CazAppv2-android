package com.greatideas.cazapp.modules.lists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.greatideas.cazapp.R
import com.greatideas.cazapp.entity.CustomList

class ListsAdapter(
    val presenter: ListsContract.Presenter,
    var customLists: List<CustomList>?,
    val listener: (CustomList) -> Unit
) : RecyclerView.Adapter<ListsAdapter.ListViewHolder>() {
    override fun getItemCount(): Int {
        return this.customLists?.size ?: 0
    }

    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textView: TextView = view.findViewById(R.id.title_list_holder)
        var songCardView: View = view.findViewById(R.id.list_card_view)
        var lengthView: TextView = view.findViewById(R.id.size_list_holder)
        var deleteView: ImageView = view.findViewById(R.id.delete_list_holder)

        fun bindSong(customList: CustomList, listener: (CustomList) -> Unit) {
            textView.text = customList.title
            lengthView.text = "${customList.songs.size} songs"
            songCardView.setOnClickListener { listener(customList) }
            deleteView.setOnClickListener {
                presenter.onDeleteSong(customList)
                this@ListsAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bindSong(customLists!![position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_holder, parent, false)
        return ListViewHolder(itemView)
    }

}
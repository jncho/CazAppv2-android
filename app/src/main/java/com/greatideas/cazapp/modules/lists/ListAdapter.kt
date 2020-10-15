package com.greatideas.cazapp.modules.lists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.greatideas.cazapp.R
import com.greatideas.cazapp.entity.CustomList

class ListAdapter(var songs: List<CustomList>?, val listener: (CustomList) -> Unit) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    override fun getItemCount(): Int {
        return this.songs?.size ?: 0
    }

    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textView : TextView = view.findViewById(R.id.title_list_holder)
        var songCardView: View = view.findViewById(R.id.list_card_view)
        var lengthView: TextView = view.findViewById(R.id.size_list_holder)

        fun bindSong(favoriteSong: CustomList, listener: (CustomList) -> Unit){
            textView.text = favoriteSong.title
            lengthView.text ="${favoriteSong.songs.size} songs"
            songCardView.setOnClickListener{listener(favoriteSong)}

        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bindSong(songs!![position],listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.list_holder, parent, false)
        return ListViewHolder(itemView)
    }

}
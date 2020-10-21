package com.greatideas.cazapp.modules.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.greatideas.cazapp.R
import com.greatideas.cazapp.entity.CustomList
import com.greatideas.cazapp.entity.ListSong

class ListAdapter(var customList: CustomList?,var songs: List<ListSong>?, val listener: (CustomList,ListSong) -> Unit) : RecyclerView.Adapter<ListAdapter.ListSongViewHolder>() {
    override fun getItemCount(): Int {
        return this.songs?.size ?: 0
    }

    inner class ListSongViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textView : TextView = view.findViewById(R.id.title_list_song_holder)
        var songCardView: View = view.findViewById(R.id.list_song_card_view)
        var sectionView: TextView = view.findViewById(R.id.section_list_song_holder)

        fun bindSong(customList: CustomList,listSong: ListSong, listener: (CustomList,ListSong) -> Unit){
            textView.text = listSong.altTitle
            sectionView.text =listSong.song?.section
            songCardView.setOnClickListener{listener(customList,listSong)}

        }
    }

    override fun onBindViewHolder(holder: ListSongViewHolder, position: Int) {
        holder.bindSong(customList!!,songs!![position],listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSongViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.list_song_holder, parent, false)
        return ListSongViewHolder(itemView)
    }

}
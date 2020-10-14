package com.greatideas.cazapp.modules.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.greatideas.cazapp.R
import com.greatideas.cazapp.entity.SearchSong
import com.greatideas.cazapp.entity.Song
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

class ResultsSearchAdapter(var songs: List<SearchSong>?, val listener: (SearchSong) -> Unit) : RecyclerView.Adapter<ResultsSearchAdapter.SearchSongViewHolder>() {
    override fun getItemCount(): Int {
        return this.songs?.size ?: 0
    }

    inner class SearchSongViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textView : TextView = view.findViewById(R.id.title_song_holder)
        var songCardView: View = view.findViewById(R.id.song_card_view)
        var sectionView: TextView = view.findViewById(R.id.section_song_holder)

        fun bindSong(song: SearchSong, listener: (SearchSong) -> Unit){
            textView.text = song.title
            sectionView.text = song.section
            songCardView.setOnClickListener{listener(song)}

        }
    }

    override fun onBindViewHolder(holder: SearchSongViewHolder, position: Int) {
        holder.bindSong(songs!![position],listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchSongViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.song_holder, parent, false)
        return SearchSongViewHolder(itemView)
    }

}
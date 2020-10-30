package com.greatideas.cazapp.modules.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.greatideas.cazapp.R
import com.greatideas.cazapp.entity.FavoriteSong
import com.greatideas.cazapp.entity.SearchSong

class FavoritesAdapter(var presenter: FavoritesContract.Presenter,var songs: List<FavoriteSong>?, val listener: (FavoriteSong) -> Unit) : RecyclerView.Adapter<FavoritesAdapter.FavoriteSongViewHolder>() {
    override fun getItemCount(): Int {
        return this.songs?.size ?: 0
    }

    inner class FavoriteSongViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textView : TextView = view.findViewById(R.id.title_favorite_song_holder)
        var songCardView: View = view.findViewById(R.id.favorite_song_card_view)
        var sectionView: TextView = view.findViewById(R.id.section_favorite_song_holder)
        var deleteView: ImageView = view.findViewById(R.id.delete_favorite_song_holder)

        fun bindSong(favoriteSong: FavoriteSong, listener: (FavoriteSong) -> Unit){
            textView.text = favoriteSong.altTitle
            sectionView.text = favoriteSong.song?.section
            songCardView.setOnClickListener{listener(favoriteSong)}
            deleteView.setOnClickListener { presenter.onDeleteSong(favoriteSong) }
        }
    }

    override fun onBindViewHolder(holder: FavoriteSongViewHolder, position: Int) {
        holder.bindSong(songs!![position],listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteSongViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.favorite_song_holder, parent, false)
        return FavoriteSongViewHolder(itemView)
    }

}
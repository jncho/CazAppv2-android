package com.greatideas.cazapp.modules.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.greatideas.cazapp.R
import com.greatideas.cazapp.entity.CustomList
import com.greatideas.cazapp.entity.ListSong

class ListAdapter(val presenter: ListContract.Presenter,var customList: CustomList?,var songs: MutableList<ListSong>?,val listenerDrag: (RecyclerView.ViewHolder) -> Unit) : RecyclerView.Adapter<ListAdapter.ListSongViewHolder>(), TouchHelperCallback.ItemTouchHelperAdapter {
    override fun getItemCount(): Int {
        return this.songs?.size ?: 0
    }

    inner class ListSongViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        var dragView : ImageView = view.findViewById(R.id.drag_view_song_holder)
        var textView : TextView = view.findViewById(R.id.title_list_song_holder)
        var songCardView: View = view.findViewById(R.id.list_song_card_view)
        var sectionView: TextView = view.findViewById(R.id.section_list_song_holder)
        var deleteView: ImageView = view.findViewById(R.id.delete_list_song_holder)

        @SuppressLint("ClickableViewAccessibility")
        fun bindSong(customList: CustomList,listSong: ListSong){
            textView.text = listSong.altTitle
            sectionView.text =listSong.song?.section
            songCardView.setOnClickListener{presenter.onListSongSelected(customList,listSong)}
            dragView.setOnTouchListener { _, event ->
                if (event.getAction()
                    == MotionEvent.ACTION_DOWN) {
                    // Notify ItemTouchHelper to start dragging
                    listenerDrag(this)
                }
                false
            }
            deleteView.setOnClickListener {
                presenter.onDeleteSong(listSong)
            }

        }
    }

    override fun onBindViewHolder(holder: ListSongViewHolder, position: Int) {
        holder.bindSong(customList!!,songs!![position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSongViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.list_song_holder, parent, false)
        return ListSongViewHolder(itemView)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        presenter.onListSongMoved(songs!!,fromPosition,toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }
}
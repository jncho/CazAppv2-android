package com.greatideas.cazapp.modules.detailsong

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.greatideas.cazapp.R
import com.greatideas.cazapp.entity.Song
import com.greatideas.cazapp.modules.main.MainActivity
import kotlinx.android.synthetic.main.detail_song_fragment.*
import kotlinx.android.synthetic.main.search_fragment.*

class DetailSongFragment : Fragment() , DetailSongContract.View{

    lateinit var presenter: DetailSongContract.Presenter
    lateinit var snackbarMessage: Snackbar
    lateinit var song: Song

    private var tune = 0
    private var fontSize = 15f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.detail_song_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = DetailSongPresenter(this)
        presenter.getSong(arguments?.getString("idSearchSong")!!)

        snackbarMessage = Snackbar.make(fragment_detail_song_view,"", Snackbar.LENGTH_LONG)
    }

    @SuppressLint("RestrictedApi")
    override fun onResume() {

        presenter.onViewCreated(findNavController())
        (activity as MainActivity).drawerToggle.isDrawerIndicatorEnabled = false
        (activity as MainActivity).actionbar.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).toolbar.setNavigationOnClickListener{
            presenter.backButtonClicked()
        }
        super.onResume()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_song_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId){
        R.id.action_favorite -> {
            presenter.addFavorite(song,tune,fontSize)
            true
        }
        else -> false
    }

    override fun updateView(song: Song){
        this.song = song
        (activity as MainActivity).actionbar.title = song.title
        drawSong()
    }

    private fun drawSong() {
        song_layout.removeAllViews()
        for (line in song.body) {
            val lineView = TextView(requireActivity())

            lineView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
            lineView.text = line.content

            when (line.type) {
                "normal" -> {
                    lineView.typeface = Typeface.MONOSPACE
                    lineView.setTextColor(Color.parseColor("#2c2c2c"))
                }
                "estribillo" -> {
                    lineView.setTypeface(Typeface.MONOSPACE, Typeface.BOLD)
                    lineView.setTextColor(Color.parseColor("#002183"))
                }
                "acorde" -> {
                    lineView.setTypeface(Typeface.MONOSPACE, Typeface.BOLD)
                    lineView.setTextColor(Color.parseColor("#949494"))
                }
            }

            song_layout.addView(lineView)
        }
    }

    override fun showMessage(message: String) {
        snackbarMessage.setText(message).show()
    }

}
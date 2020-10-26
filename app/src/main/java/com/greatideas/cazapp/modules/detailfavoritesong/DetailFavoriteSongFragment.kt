package com.greatideas.cazapp.modules.detailfavoritesong

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.opengl.Visibility
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.greatideas.cazapp.R
import com.greatideas.cazapp.entity.FavoriteSong
import com.greatideas.cazapp.modules.main.MainActivity
import kotlinx.android.synthetic.main.detail_favorite_song_fragment.*

class DetailFavoriteSongFragment : Fragment(), DetailFavoriteSongContract.View {

    lateinit var presenter: DetailFavoriteSongContract.Presenter
    lateinit var snackbarMessage: Snackbar
    lateinit var favoriteSong: FavoriteSong

    lateinit var menu: Menu


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detail_favorite_song_menu, menu)
        this.menu = menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.actionShowEditButtons -> {
            showEditButtons()
            true
        }
        R.id.actionHideEditButtons -> {
            hideEditButtons()
            true
        }
        else -> false
    }

    fun hideEditButtons(){
        editButtons.visibility = View.GONE
        menu.findItem(R.id.actionHideEditButtons).isVisible = false
        menu.findItem(R.id.actionShowEditButtons).isVisible = true
    }

    fun showEditButtons(){
        editButtons.visibility = View.VISIBLE
        menu.findItem(R.id.actionHideEditButtons).isVisible = true
        menu.findItem(R.id.actionShowEditButtons).isVisible = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.detail_favorite_song_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = DetailFavoriteSongPresenter(this)

        snackbarMessage =
            Snackbar.make(fragment_detail_favorite_song_view, "", Snackbar.LENGTH_LONG)
        presenter.getSong(arguments?.getString("idSearchSong")!!)

        toneUpActionButton.setOnClickListener { presenter.onActionUpSemitone(favoriteSong) }
        toneDownActionButton.setOnClickListener { presenter.onActionDownSemitone(favoriteSong) }
        textsizeDownActionButton.setOnClickListener { presenter.onActionDownSize(favoriteSong) }
        textsizeUpActionButton.setOnClickListener { presenter.onActionUpSize(favoriteSong) }
    }

    @SuppressLint("RestrictedApi")
    override fun onResume() {

        presenter.onViewCreated(findNavController())
        (activity as MainActivity).drawerToggle.isDrawerIndicatorEnabled = false
        (activity as MainActivity).actionbar.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).toolbar.setNavigationOnClickListener {
            presenter.backButtonClicked()
        }
        super.onResume()

    }

    override fun updateView(song: FavoriteSong) {
        this.favoriteSong = song
        (activity as MainActivity).actionbar.title = song.altTitle
        drawSong()
    }

    private fun drawSong() {
        favorite_song_layout.removeAllViews()
        for (line in favoriteSong.song!!.body) {
            val lineView = TextView(requireActivity())

            lineView.setTextSize(TypedValue.COMPLEX_UNIT_SP, favoriteSong.fontSize)
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

            favorite_song_layout.addView(lineView)
        }
    }

    override fun showMessage(message: String) {
        snackbarMessage.setText(message).show()
    }

}
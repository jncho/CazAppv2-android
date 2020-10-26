package com.greatideas.cazapp.modules.detailfavoritesong

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.greatideas.cazapp.R
import com.greatideas.cazapp.entity.FavoriteSong
import com.greatideas.cazapp.entity.ListSong
import com.greatideas.cazapp.modules.main.MainActivity
import kotlinx.android.synthetic.main.detail_list_song_fragment.*
import kotlinx.android.synthetic.main.detail_list_song_fragment.editButtons
import kotlinx.android.synthetic.main.detail_list_song_fragment.toneDownActionButton
import kotlinx.android.synthetic.main.detail_list_song_fragment.toneUpActionButton

class DetailListSongFragment : Fragment() , DetailListSongContract.View{

    lateinit var presenter: DetailListSongContract.Presenter
    lateinit var snackbarMessage: Snackbar
    lateinit var listSong: ListSong

    lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detail_list_song_menu,menu)
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.detail_list_song_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = DetailListSongPresenter(this)

        snackbarMessage = Snackbar.make(fragment_detail_list_song_view,"", Snackbar.LENGTH_LONG)
        presenter.getListSong(arguments?.getString("idCustomList")!!,arguments?.getString("idListSong")!!)

        toneUpActionButton.setOnClickListener { presenter.onActionUpSemitone(listSong) }
        toneDownActionButton.setOnClickListener { presenter.onActionDownSemitone(listSong) }
        textsizeDownActionButton.setOnClickListener { presenter.onActionDownSize(listSong) }
        textsizeUpActionButton.setOnClickListener { presenter.onActionUpSize(listSong) }
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

    override fun updateView(listSong: ListSong){
        this.listSong = listSong
        (activity as MainActivity).actionbar.title = listSong.altTitle
        drawSong()
    }

    private fun drawSong() {
        list_song_layout.removeAllViews()
        for (line in listSong.song!!.body) {
            val lineView = TextView(requireActivity())

            lineView.setTextSize(TypedValue.COMPLEX_UNIT_SP, listSong.fontSize)
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

            list_song_layout.addView(lineView)
        }
    }

    override fun showMessage(message: String) {
        snackbarMessage.setText(message).show()
    }

}
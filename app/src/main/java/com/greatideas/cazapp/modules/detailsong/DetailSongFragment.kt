package com.greatideas.cazapp.modules.detailsong

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.marginEnd
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.greatideas.cazapp.R
import com.greatideas.cazapp.entity.Song
import com.greatideas.cazapp.modules.main.MainActivity
import kotlinx.android.synthetic.main.detail_song_fragment.*
import kotlinx.android.synthetic.main.input_text_dialog.*
import kotlinx.android.synthetic.main.search_fragment.*

class DetailSongFragment : Fragment(), DetailSongContract.View {

    lateinit var presenter: DetailSongContract.Presenter
    lateinit var snackbarMessage: Snackbar
    lateinit var song: Song

    private var tune = 0
    private var fontSize = 15f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.detail_song_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = DetailSongPresenter(this)
        presenter.getSong(arguments?.getString("idSearchSong")!!)

        snackbarMessage = Snackbar.make(fragment_detail_song_view, "", Snackbar.LENGTH_LONG)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_song_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_favorite -> {
            presenter.addFavorite(song, tune, fontSize)
            true
        }
        R.id.action_add_list -> {
            SelectListDialog(presenter, song, tune, fontSize).show(parentFragmentManager, "lists")
            true
        }
        else -> false
    }

    override fun updateView(song: Song) {
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

    class SelectListDialog(
        var presenter: DetailSongContract.Presenter,
        var song: Song,
        var tune: Int,
        var fontSize: Float
    ) : DialogFragment() {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val titles = presenter.getTitlesCustomLists()
            return activity?.let {
                // Use the Builder class for convenient dialog construction
                val builder = AlertDialog.Builder(it)
                builder.setTitle("Pick a list")
                    .setItems(titles?.values?.toTypedArray()) { context, index ->
                        presenter.onCustomListSelect(
                            titles!!.keys.elementAt(index),
                            song,
                            tune,
                            fontSize
                        )
                    }
                    .setPositiveButton("New List") { dialog, id ->
                        // FIRE ZE MISSILES!
                        NewListDialog(presenter,song,tune,fontSize).show(it.supportFragmentManager, "newlistdialog")
                    }
                    .setNegativeButton("Cancel") { dialog, id ->
                        getDialog()?.cancel()
                    }
                // Create the AlertDialog object and return it
                builder.create()
            } ?: throw IllegalStateException("Activity cannot be null")
        }
    }

    class NewListDialog(
        var presenter: DetailSongContract.Presenter,
        var song: Song,
        var tune: Int,
        var fontSize: Float
    ) : DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.setTitle("Create New List")

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(activity?.layoutInflater?.inflate(R.layout.input_text_dialog, null))

                // Add action buttons
                builder.setPositiveButton("Create") { dialog, id ->
                    val editTextView =
                        (dialog as Dialog).findViewById<EditText>(R.id.edit_text_input_text_dialog)
                    presenter.onCreateNewList(editTextView!!.text.toString())

                    SelectListDialog(presenter, song, tune, fontSize).show(
                        parentFragmentManager,
                        "lists"
                    )
                }
                    .setNegativeButton("Cancel") { dialog, id ->
                        getDialog()?.cancel()
                        SelectListDialog(presenter, song, tune, fontSize).show(
                            parentFragmentManager,
                            "lists"
                        )
                    }


                builder.create()
            } ?: throw IllegalStateException("Activity cannot be null")
        }
    }


}
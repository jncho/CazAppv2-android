package com.greatideas.cazapp.modules.list

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.view.*
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.greatideas.cazapp.R
import com.greatideas.cazapp.entity.CustomList
import com.greatideas.cazapp.modules.main.MainActivity
import kotlinx.android.synthetic.main.list_fragment.*
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class ListFragment : Fragment(), ListContract.View {

    lateinit var presenter: ListContract.Presenter
    lateinit var adapterList: ListAdapter
    private lateinit var touchHelper: ItemTouchHelper
    lateinit var snackbarMessage: Snackbar

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
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ListPresenter(this)

        snackbarMessage = Snackbar.make(fragment_list_view,"", Snackbar.LENGTH_LONG)

        // Recycler View
        adapterList = ListAdapter(presenter,null,null){ viewHolder ->
            touchHelper.startDrag(viewHolder)
        }
        customlist_list.apply {
            layoutManager = LinearLayoutManager(activity)
            this.adapter = adapterList
            setHasFixedSize(true)
            val callback = TouchHelperCallback(adapter as TouchHelperCallback.ItemTouchHelperAdapter)
            touchHelper = ItemTouchHelper(callback)
            touchHelper.attachToRecyclerView(this)
        }
    }

    override fun onResume() {
        presenter.onViewCreated(findNavController())
        presenter.getCustomList(arguments?.getString("idCustomList")!!)

        (activity as MainActivity).drawerToggle.isDrawerIndicatorEnabled = false
        (activity as MainActivity).actionbar.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).toolbar.setNavigationOnClickListener{
            presenter.backButtonClicked()
        }
        super.onResume()
    }

    override fun updateRecyclerView(customList: CustomList?) {
        if (customList != null) {
            (activity as MainActivity).actionbar.title = customList.title
            adapterList.customList = customList
            adapterList.songs = customList.songs
        }
        adapterList.notifyDataSetChanged()
    }

    override fun showMessage(message: String) {
        snackbarMessage.setText(message).show()
    }

    override fun showDeletedAlertDialog(title: String, message: String, callback: () -> Unit) {

        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle(title)
                setMessage(message)
                setPositiveButton("Ok") { _, _ ->
                    callback()
                }
                setNegativeButton("Cancel",null)
            }
            // Set other dialog properties


            // Create the AlertDialog
            builder.create()
        }
        alertDialog?.show()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.export_to_docx -> {
            presenter.exportToDocx(arguments?.getString("idCustomList")!!)
            true
        }
        else -> false
    }

    override fun shareDocx(docx: XWPFDocument, listName: String) {

        val wordFile = File(requireContext().externalCacheDir, "${listName}.docx")
        try {
            val fileOut = FileOutputStream(wordFile)
            docx.write(fileOut)
            fileOut.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }


        val shareIntent: Intent = Intent().apply {
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(requireContext(), requireContext().getApplicationContext().getPackageName() + ".provider", wordFile))
            type = "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
        }
        val chooser = Intent.createChooser(shareIntent, "Share File")

        val resInfoList: List<ResolveInfo> = requireContext().getPackageManager()
            .queryIntentActivities(chooser, PackageManager.MATCH_DEFAULT_ONLY)

        for (resolveInfo in resInfoList) {
            val packageName = resolveInfo.activityInfo.packageName
            requireContext().grantUriPermission(
                packageName,
                FileProvider.getUriForFile(requireContext(), requireContext().getApplicationContext().getPackageName() + ".provider", wordFile),
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }

        startActivity(chooser)

    }

}
package com.greatideas.cazapp.modules.search

import android.app.Application
import android.os.Parcelable
import androidx.lifecycle.AndroidViewModel
import com.greatideas.cazapp.entity.SearchSong

class BatchViewModel(application: Application) : AndroidViewModel(application) {
    var listState: Parcelable? = null
    var songs: List<SearchSong>? = null
}
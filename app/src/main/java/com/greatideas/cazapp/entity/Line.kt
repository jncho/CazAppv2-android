package com.greatideas.cazapp.entity

import io.realm.RealmObject
import kotlinx.android.parcel.Parcelize
import org.bson.Document

open class Line(
    var line: Int = -1,
    var type: String = "",
    var content: String = ""
) : RealmObject() {
}
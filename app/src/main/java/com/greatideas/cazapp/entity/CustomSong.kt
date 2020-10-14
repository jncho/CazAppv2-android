package com.greatideas.cazapp.entity

import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId

open class FavoriteSong (
    @PrimaryKey var id: ObjectId? = null,
    var song: Song? = null,
    var tune: Int = 0,
    var fontSize: Float = 10f,
    var altTitle: String? = null
) : RealmObject()


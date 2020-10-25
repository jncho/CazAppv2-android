package com.greatideas.cazapp.entity

import io.realm.RealmObject
import kotlinx.android.parcel.Parcelize
import org.bson.Document
import java.util.*

open class Line(
    var line: Int = -1,
    var type: String = "",
    var content: String = ""
) : RealmObject() {

    fun trasnpose(semitones: Int): Boolean{
        if (type != "acorde"){
           return false
        }
        val pattern = Regex("([a-z]+[#b]?)[m]?", RegexOption.IGNORE_CASE)
        content = content.replace(pattern){
            val noteRead = it.groupValues[1]
            val ordinal = Note.getOrdinalNote(noteRead)
            val notes = Note.values()
            val newOrdinal = (ordinal+semitones) % notes.size
            var result = notes[newOrdinal].label
            if (noteRead == it.value.toUpperCase(Locale.getDefault())){
                result = result.toUpperCase(Locale.getDefault())
            }
            result
        }
        return true
    }

}



enum class Note (val label : String ,val alt_label : String ){
    DO("do","do"),
    DO_("do#","reb"),
    RE("re","re"),
    RE_("re#","mib"),
    MI("mi","mi"),
    FA("fa","fa"),
    FA_("fa#","solb"),
    SOL("sol","sol"),
    SOL_("sol#","lab"),
    LA("la","la"),
    LA_("la#","sib"),
    SI("si","si");

    companion object {
        fun getOrdinalNote(note: String) : Int {
            values().forEach {
                if (it.label.equals(note,true) || it.alt_label.equals(note,true)){
                    return it.ordinal
                }
            }
            return -1
        }
    }

}
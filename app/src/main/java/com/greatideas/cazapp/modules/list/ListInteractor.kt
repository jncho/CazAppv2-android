package com.greatideas.cazapp.modules.list

import com.greatideas.cazapp.entity.CustomList
import com.greatideas.cazapp.entity.ListSong
import com.greatideas.cazapp.localRealm
import io.realm.kotlin.where
import org.apache.poi.xwpf.usermodel.ParagraphAlignment
import org.apache.poi.xwpf.usermodel.UnderlinePatterns
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.bson.types.ObjectId
import java.text.Normalizer
import java.util.*

class ListInteractor : ListContract.Interactor {

    override fun getCustomList(idList: String,listener: (CustomList) -> Unit) {
        val customList = localRealm.where<CustomList>().equalTo("id", ObjectId( idList)).findFirst()!!
        listener(customList)
    }

    override fun deleteSong(listSong: ListSong, callback: () -> Unit) {
        localRealm.executeTransaction {
            listSong.deleteFromRealm()
            callback()
        }
    }

    override fun exportToDocx(idList: String, callback: (XWPFDocument, String) -> Unit) {
        getCustomList(idList) {
            val ourWordDocx = XWPFDocument()
            for (song in it.songs){
                val paragraph1 = ourWordDocx.createParagraph()
                paragraph1.alignment = ParagraphAlignment.LEFT
                val titleSong = paragraph1.createRun()
                titleSong.fontSize = 12
                titleSong.fontFamily = "Consolas"
                titleSong.isBold = true
                titleSong.underline = UnderlinePatterns.SINGLE
                titleSong.isItalic = true
                titleSong.setText(song.song!!.title)
                titleSong.addBreak()
                titleSong.addBreak()

                for (line in song.song!!.body){
                    val sentenceRun1 = paragraph1.createRun()
                    sentenceRun1.fontSize = 12
                    sentenceRun1.fontFamily = "Consolas"
                    //format the text
                    if (line.type == "acorde"){
                        sentenceRun1.isBold = true
                    } else if (line.type == "estribillo"){
                        sentenceRun1.isBold = true
                        sentenceRun1.color = "3069B0"
                    }



                    sentenceRun1.setText(line.content)
                    //add a sentence break
                    sentenceRun1.addBreak()
                }

                paragraph1.isPageBreak = true
            }

            callback(ourWordDocx, slugify(it.title))

        }
    }

    private fun slugify(word: String, replacement: String = "-") = Normalizer
        .normalize(word, Normalizer.Form.NFD)
        .replace("[^\\p{ASCII}]".toRegex(), "")
        .replace("[^a-zA-Z0-9\\s]+".toRegex(), "").trim()
        .replace("\\s+".toRegex(), replacement)
        .toLowerCase(Locale.getDefault())

}
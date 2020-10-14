package com.greatideas.cazapp.entity

import io.realm.RealmList
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parcelize
import org.bson.BsonReader
import org.bson.codecs.Decoder
import org.bson.codecs.DecoderContext
import org.bson.types.ObjectId

open class Song(
    @PrimaryKey var _id: ObjectId? = null,
    var _partition: String? = "Public Songs",
    var title: String = "",
    var section: String = "",
    var body: RealmList<Line> = RealmList()
) : RealmObject()

class SongDecoder() : Decoder<Song>{
    override fun decode(reader: BsonReader, decoderContext: DecoderContext?): Song {
        reader.readStartDocument()
        val song = Song()
        song._id = reader.readObjectId()
        song.title = reader.readString()
        reader.readStartArray()
        try {
            while (true) {
                reader.readStartDocument()
                val line = Line()
                line.line = reader.readInt32()
                line.type = reader.readString()
                line.content = reader.readString()
                reader.readEndDocument()
                song.body.add(line)
            }
        } catch (e: Exception) {
        }
        reader.readEndArray()
        song.section = reader.readString()
        song._partition = reader.readString()

        reader.readEndDocument()
        return song
    }
}

class SongsDecoder() : Decoder<List<Song>> {
    override fun decode(reader: BsonReader, decoderContext: DecoderContext?): List<Song> {
        val songs = mutableListOf<Song>()
        reader.readStartArray()
        try {
            while (true) {
                reader.readStartDocument()

                val song = Song()
                song._id = reader.readObjectId()
                song.title = reader.readString()
                reader.readStartArray()
                try {
                    while (true) {
                        reader.readStartDocument()
                        val line = Line()
                        line.line = reader.readInt32()
                        line.type = reader.readString()
                        line.content = reader.readString()
                        reader.readEndDocument()
                        song.body.add(line)
                    }
                } catch (e: Exception) {
                }
                reader.readEndArray()
                song.section = reader.readString()
                song._partition = reader.readString()
                songs.add(song)

                reader.readEndDocument()
            }
        } catch (e: Exception) {
        }
        reader.readEndArray()

        return songs

    }
}
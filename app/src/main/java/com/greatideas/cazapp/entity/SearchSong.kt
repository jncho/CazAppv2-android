package com.greatideas.cazapp.entity

import org.bson.BsonReader
import org.bson.codecs.Decoder
import org.bson.codecs.DecoderContext
import org.bson.types.ObjectId

class SearchSong (
    var id: ObjectId,
    var title: String,
    var section: String
    )

class SearchSongsDecoder : Decoder<List<SearchSong>> {
    override fun decode(reader: BsonReader, decoderContext: DecoderContext?): List<SearchSong> {
        val songs = mutableListOf<SearchSong>()
        reader.readStartArray()

        var id : ObjectId
        var title: String
        var section: String
        try {
            while (true) {
                reader.readStartDocument()

                id= reader.readObjectId()
                title = reader.readString()
                section = reader.readString()
                songs.add(SearchSong(id, title, section))

                reader.readEndDocument()
            }
        } catch (e: Exception) {
        }
        reader.readEndArray()
        return songs

    }
}
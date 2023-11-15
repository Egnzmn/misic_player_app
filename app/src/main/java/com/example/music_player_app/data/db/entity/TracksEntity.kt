package com.example.music_player_app.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.music_player_app.data.TracksDataResponse.TrackResponse

@Entity(tableName = "track_table")
data class TracksEntity(
    @PrimaryKey val id: String,
    val albumId: String,
    val albumImage: String,
    val albumName: String,
    val artistId: String,
    val artistName: String,
    val audio: String,
    val duration: Int,
    val image: String,
    val name: String,
    val position: Int,
    val releaseDate: String
)

fun List<TrackResponse>.toEntity() = this.map {
    TracksEntity(
        albumId = it.albumId ?: "",
        albumImage = it.albumImage ?: "",
        albumName = it.albumName ?: "",
        artistId = it.artistId ?: "",
        artistName = it.artistName ?: "",
        audio = it.audio ?: "",
        duration = it.duration ?: 0,
        id = it.id ?: "",
        image = it.image ?: "",
        name = it.name ?: "",
        position = it.position ?: 0,
        releaseDate = it.releaseDate ?: ""
    )
}

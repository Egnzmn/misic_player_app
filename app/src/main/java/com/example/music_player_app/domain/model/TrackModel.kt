package com.example.music_player_app.domain.model

import com.example.music_player_app.data.TracksDataResponse.TrackResponse

data class TrackModel(
    val albumId: String = "",
    val albumImage: String = "",
    val albumName: String = "",
    val artistId: String = "",
    val artistName: String = "",
    val audio: String = "",
    val duration: Int = 0,
    val id: String = "",
    val image: String = "",
    val name: String = "",
    val position: Int = 0,
    val releaseDate: String = ""
)

fun List<TrackResponse>.toModel() = this.map {
    TrackModel(
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

fun TrackResponse.toModel(): TrackModel =
    TrackModel(
        albumId = albumId ?: "",
        albumImage = albumImage ?: "",
        albumName = albumName ?: "",
        artistId = artistId ?: "",
        artistName = artistName ?: "",
        audio = audio ?: "",
        duration = duration ?: 0,
        id = id ?: "",
        image = image ?: "",
        name = name ?: "",
        position = position ?: 0,
        releaseDate = releaseDate ?: ""
    )

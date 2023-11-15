package com.example.music_player_app.data

import com.google.gson.annotations.SerializedName

data class TracksDataResponse(
    @SerializedName("results") val results: List<TrackResponse>
) {
    data class TrackResponse(
        @SerializedName("album_id") val albumId: String?,
        @SerializedName("album_image") val albumImage: String?,
        @SerializedName("album_name") val albumName: String?,
        @SerializedName("artist_id") val artistId: String?,
        @SerializedName("artist_idstr") val artistIdstr: String?,
        @SerializedName("artist_name") val artistName: String?,
        @SerializedName("audio") val audio: String?,
        @SerializedName("audiodownload") val audioDownload: String?,
        @SerializedName("audiodownload_allowed") val audioDownloadAllowed: Boolean?,
        @SerializedName("duration") val duration: Int?,
        @SerializedName("id") val id: String?,
        @SerializedName("image") val image: String?,
        @SerializedName("name") val name: String?,
        @SerializedName("position") val position: Int?,
        @SerializedName("prourl") val proUrl: String?,
        @SerializedName("releasedate") val releaseDate: String?
    )
}

package com.example.music_player_app.domain.repository

import com.example.music_player_app.domain.model.TrackModel

interface TracksRepository {

    suspend fun getTracks(): List<TrackModel>

    suspend fun getTrackInfo(id: String): TrackModel

    suspend fun getTracksIDs(): List<String>
}

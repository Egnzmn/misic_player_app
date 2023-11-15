package com.example.music_player_app.data.repository

import com.example.music_player_app.data.api.JamendoApi
import com.example.music_player_app.data.db.TracksDao
import com.example.music_player_app.data.db.entity.toEntity
import com.example.music_player_app.domain.model.TrackModel
import com.example.music_player_app.domain.model.toModel
import com.example.music_player_app.domain.repository.TracksRepository
import javax.inject.Inject

class TracksRepositoryImpl @Inject constructor(
    private val jamendoApi: JamendoApi,
    private val tracksDao: TracksDao
) : TracksRepository {

    override suspend fun getTracks(): List<TrackModel> {
        tracksDao.insertTracks(jamendoApi.getTracks().results.toEntity())
        return jamendoApi.getTracks().results.toModel()
    }

    override suspend fun getTrackInfo(id: String): TrackModel {
        return jamendoApi.getTrackInfo(id = id).results.first().toModel()
    }

    override suspend fun getTracksIDs(): List<String> {
        return tracksDao.getTracksIDs()
    }
}

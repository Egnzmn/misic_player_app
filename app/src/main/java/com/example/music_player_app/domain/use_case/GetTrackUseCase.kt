package com.example.music_player_app.domain.use_case

import com.example.music_player_app.domain.model.TrackModel
import com.example.music_player_app.domain.repository.TracksRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetTrackUseCase @Inject constructor(
    private val repo: TracksRepository
) {

    suspend fun getTracks(): List<TrackModel> {
        return withContext(Dispatchers.IO) { repo.getTracks() }
    }
}

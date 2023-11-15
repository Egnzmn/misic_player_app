package com.example.music_player_app.domain.use_case

import com.example.music_player_app.domain.repository.TracksRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetTracksIDsUseCase @Inject constructor(
    private val repo: TracksRepository
) {

    suspend fun getTracksIDs(): List<String> {
        return withContext(Dispatchers.IO) { repo.getTracksIDs() }
    }
}

package com.example.music_player_app.ui.screen.playlist_screen

import androidx.lifecycle.viewModelScope
import com.example.music_player_app.domain.use_case.GetTrackUseCase
import com.example.music_player_app.ui.screen.playlist_screen.PlaylistAction.FetchTrackData
import com.example.music_player_app.ui.screen.playlist_screen.PlaylistAction.OnTrackItemClicked
import com.example.music_player_app.ui.screen.playlist_screen.PlaylistEffect.OpenMusicPlayerScreen
import com.example.music_player_app.util.base.BaseMVIViewModel
import com.example.music_player_app.util.base.UIAction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PlaylistVM @Inject constructor(
    private val getTrackUseCase: GetTrackUseCase
) : BaseMVIViewModel<PlaylistState>(PlaylistState()) {

    override fun handleAction(action: UIAction) {
        when (action) {
            is FetchTrackData -> fetchTracksData()
            is OnTrackItemClicked -> openMusicPlayerScreen(action.id)
        }
    }

    private fun fetchTracksData() {
        viewModelScope.launch {
            setState(currentState.copy(data = getTrackUseCase.getTracks()))
        }
    }

    private fun openMusicPlayerScreen(id: String) = setEffect(OpenMusicPlayerScreen(id))
}

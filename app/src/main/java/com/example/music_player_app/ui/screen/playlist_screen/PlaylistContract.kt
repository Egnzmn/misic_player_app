package com.example.music_player_app.ui.screen.playlist_screen

import com.example.music_player_app.domain.model.TrackModel
import com.example.music_player_app.util.base.UIAction
import com.example.music_player_app.util.base.UIEffect
import com.example.music_player_app.util.base.UIState

sealed class PlaylistAction : UIAction {
    class FetchTrackData : PlaylistAction()
    class OnTrackItemClicked (val id: String): PlaylistAction()
}

data class PlaylistState(
    val data: List<TrackModel> = emptyList()
) : UIState

sealed class PlaylistEffect : UIEffect {
    class OpenMusicPlayerScreen(val id: String): PlaylistEffect()
}

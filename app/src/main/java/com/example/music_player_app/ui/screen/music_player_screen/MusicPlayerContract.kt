package com.example.music_player_app.ui.screen.music_player_screen

import com.example.music_player_app.domain.model.TrackModel
import com.example.music_player_app.util.base.UIAction
import com.example.music_player_app.util.base.UIEffect
import com.example.music_player_app.util.base.UIState

sealed class MusicPlayerAction : UIAction {
    class InitMusicPlayerScreen(val trackId: String) : MusicPlayerAction()
    class InitPlayer : MusicPlayerAction()
    class OnPlayButtonClicked(val url: String, val id: String) : MusicPlayerAction()
    class UpdateCurrentPosition : MusicPlayerAction()
    class SeekToPosition(val value: Float) : MusicPlayerAction()
    class OnPauseButtonClicked : MusicPlayerAction()
    class OnSeekForwardClicked : MusicPlayerAction()
    class OnSeekBackClicked : MusicPlayerAction()
    class OnNextTrackClicked(val id: String) : MusicPlayerAction()
    class OnPreviousTrackClicked(val id: String, val url: String) : MusicPlayerAction()
    class OnEnableRepeatModeClicked : MusicPlayerAction()
    class OnDisableRepeatModeClicked : MusicPlayerAction()
    class OnBackButtonClicked : MusicPlayerAction()
}

data class MusicPlayerState(
    val data: TrackModel = TrackModel(),
    val currentPosition: Long = 0L,
    val progress: Float = 0f,
    val isPlaying: Boolean = false,
    val playbackState: Int = 0,
    val isRepeatModeOn: Boolean = false,
    val isTrackFinished: Boolean = false
) : UIState

sealed class MusicPlayerEffect : UIEffect {
    class OpenPlaylistScreen : MusicPlayerEffect()
}

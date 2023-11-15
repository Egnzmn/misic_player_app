package com.example.music_player_app.ui.screen.music_player_screen

import androidx.lifecycle.viewModelScope
import com.example.music_player_app.domain.use_case.GetTrackInfoUseCase
import com.example.music_player_app.domain.use_case.GetTracksIDsUseCase
import com.example.music_player_app.player.MediaExoPlayer
import com.example.music_player_app.ui.screen.music_player_screen.MusicPlayerAction.InitMusicPlayerScreen
import com.example.music_player_app.ui.screen.music_player_screen.MusicPlayerAction.InitPlayer
import com.example.music_player_app.ui.screen.music_player_screen.MusicPlayerAction.OnBackButtonClicked
import com.example.music_player_app.ui.screen.music_player_screen.MusicPlayerAction.OnDisableRepeatModeClicked
import com.example.music_player_app.ui.screen.music_player_screen.MusicPlayerAction.OnEnableRepeatModeClicked
import com.example.music_player_app.ui.screen.music_player_screen.MusicPlayerAction.OnNextTrackClicked
import com.example.music_player_app.ui.screen.music_player_screen.MusicPlayerAction.OnPauseButtonClicked
import com.example.music_player_app.ui.screen.music_player_screen.MusicPlayerAction.OnPlayButtonClicked
import com.example.music_player_app.ui.screen.music_player_screen.MusicPlayerAction.OnPreviousTrackClicked
import com.example.music_player_app.ui.screen.music_player_screen.MusicPlayerAction.OnSeekBackClicked
import com.example.music_player_app.ui.screen.music_player_screen.MusicPlayerAction.OnSeekForwardClicked
import com.example.music_player_app.ui.screen.music_player_screen.MusicPlayerAction.SeekToPosition
import com.example.music_player_app.ui.screen.music_player_screen.MusicPlayerEffect.OpenPlaylistScreen
import com.example.music_player_app.util.base.BaseMVIViewModel
import com.example.music_player_app.util.base.UIAction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MusicPlayerVM @Inject constructor(
    private val getTrackInfoUseCase: GetTrackInfoUseCase,
    private val getTracksIDsUseCase: GetTracksIDsUseCase,
    private val player: MediaExoPlayer
) : BaseMVIViewModel<MusicPlayerState>(MusicPlayerState()) {

    override fun handleAction(action: UIAction) {
        when (action) {
            is InitMusicPlayerScreen -> initMusicPlayerScreen(action.trackId)
            is InitPlayer -> initPlayer()
            is OnPlayButtonClicked -> play(action.url, action.id)
            is SeekToPosition -> seekToPosition(action.value)
            is OnPauseButtonClicked -> pause()
            is OnSeekForwardClicked -> seekForward()
            is OnSeekBackClicked -> seekBack()
            is OnNextTrackClicked -> playNextTrack(action.id)
            is OnPreviousTrackClicked -> playPreviousTrack(action.id, action.url)
            is OnEnableRepeatModeClicked -> enableRepeatMode()
            is OnDisableRepeatModeClicked -> disableRepeatMode()
            is OnBackButtonClicked -> openPlaylistScreen()
        }
    }

    private fun initMusicPlayerScreen(id: String) {
        viewModelScope.launch {
            val data = getTrackInfoUseCase.getTrackInfo(id)
            setState(currentState.copy(data = data))
        }
    }

    private fun initPlayer() {
        player.initPlayer()
    }

    private fun play(url: String, trackId: String) {
        if (currentState.currentPosition > 0) player.resume() else
            player.startPlayer(url)
        viewModelScope.launch {
            player.updateCurrentPosition().collect {
                setState(
                    currentState.copy(
                        currentPosition = if (currentState.playbackState == 4) 0 else it,
                        progress = currentState.currentPosition.toFloat() /
                                player.getCurrentDuration() * 100f,
                        playbackState = player.getPlaybackState(),
                        isPlaying = player.getIsPlayingStatus(),
                    )
                )
                setState(currentState.copy(isTrackFinished = currentState.playbackState == 4))
                val tracksIDs = getTracksIDsUseCase.getTracksIDs()
                val indexOfCurrentTrack = tracksIDs.indexOf(trackId)
                val nextSongId =
                    if (indexOfCurrentTrack == tracksIDs.lastIndex) tracksIDs[0] else tracksIDs[indexOfCurrentTrack + 1]
                if (currentState.playbackState == 4) playNextTrack(nextSongId)
            }
        }
    }

    private fun seekToPosition(value: Float) {
        player.seekToPosition(value)
    }

    private fun pause() {
        player.pausePlayer()
        setState(currentState.copy(isPlaying = false))
    }

    private fun seekForward() {
        player.seekFroward()
    }

    private fun seekBack() {
        player.seekBack()
    }

    private fun playNextTrack(trackId: String) {
        viewModelScope.launch {
            player.deInitPlayer()
            player.initPlayer()
            val tracksIDs = getTracksIDsUseCase.getTracksIDs()
            val indexOfCurrentTrack = tracksIDs.indexOf(trackId)
            val nextSongId =
                if (indexOfCurrentTrack == tracksIDs.lastIndex) tracksIDs[0] else tracksIDs[indexOfCurrentTrack + 1]
            val data = getTrackInfoUseCase.getTrackInfo(nextSongId)
            setState(currentState.copy(data = data))
            play(currentState.data.audio, trackId)
        }
    }

    private fun playPreviousTrack(trackId: String, url: String) {
        viewModelScope.launch {
            player.deInitPlayer()
            player.initPlayer()
            val tracksIDs = getTracksIDsUseCase.getTracksIDs()
            val indexOfCurrentTrack = tracksIDs.indexOf(trackId)
            val nextSongId =
                if (indexOfCurrentTrack == 0) tracksIDs[tracksIDs.size - 1] else tracksIDs[indexOfCurrentTrack - 1]
            val data = getTrackInfoUseCase.getTrackInfo(nextSongId)
            setState(currentState.copy(data = data))
            if (currentState.isPlaying)
                play(url, trackId)
        }
    }

    private fun enableRepeatMode() {
        player.enableRepeatMode()
        setState(currentState.copy(isRepeatModeOn = true))
    }

    private fun disableRepeatMode() {
        player.disableRepeatMode()
        setState(currentState.copy(isRepeatModeOn = false))
    }

    private fun openPlaylistScreen() = setEffect(OpenPlaylistScreen())

    override fun onCleared() {
        super.onCleared()
        player.deInitPlayer()
    }
}

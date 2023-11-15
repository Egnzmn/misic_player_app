package com.example.music_player_app.ui.screen.music_player_screen

import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.music_player_app.domain.use_case.GetTrackUseCase
import com.example.music_player_app.ui.screen.music_player_screen.MusicPlayerAction.InitMusicPlayerScreen
import com.example.music_player_app.ui.screen.music_player_screen.MusicPlayerAction.InitPlayer
import com.example.music_player_app.ui.screen.music_player_screen.MusicPlayerEffect.OpenPlaylistScreen
import com.example.music_player_app.ui.screen.playlist_screen.PlaylistEffect.OpenMusicPlayerScreen
import com.example.music_player_app.ui.screen.playlist_screen.PlaylistScreen
import com.example.music_player_app.util.base.ViewModel

data class MusicPlayerScreen(
    val trackId: String,
    val getTrackUseCase: GetTrackUseCase
) : Screen {

    @RequiresApi(VERSION_CODES.O)
    @Composable
    override fun Content() {
        MusicPlayerScreenCompose(vm = hiltViewModel(), trackId, getTrackUseCase)
    }
}

@RequiresApi(VERSION_CODES.O)
@Composable
fun MusicPlayerScreenCompose(vm: MusicPlayerVM, trackId: String, getTrackUseCase: GetTrackUseCase) {

    ViewModel(factory = { vm }) { viewModel ->
        val state = viewModel.uiState.collectAsState()
        val effect = viewModel.uiEffect.collectAsState(initial = null)
        val navigator = LocalNavigator.currentOrThrow

        MusicPlayerView(musicPlayerState = state.value) {
            viewModel.handleAction(it)
        }

        when (effect.value) {
            is OpenPlaylistScreen -> navigator.push(PlaylistScreen(getTrackUseCase))
        }

        LaunchedEffect(Unit) {
            vm.handleAction(InitMusicPlayerScreen(trackId))
            vm.handleAction(InitPlayer())
        }
    }
}

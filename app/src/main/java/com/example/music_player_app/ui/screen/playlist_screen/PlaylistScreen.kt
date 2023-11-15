package com.example.music_player_app.ui.screen.playlist_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.music_player_app.domain.use_case.GetTrackUseCase
import com.example.music_player_app.ui.screen.music_player_screen.MusicPlayerScreen
import com.example.music_player_app.ui.screen.playlist_screen.PlaylistAction.FetchTrackData
import com.example.music_player_app.ui.screen.playlist_screen.PlaylistEffect.OpenMusicPlayerScreen
import com.example.music_player_app.util.base.ViewModel
import javax.inject.Inject

class PlaylistScreen @Inject constructor(
    private val getTrackUseCase: GetTrackUseCase,
) : Screen {

    @Composable
    override fun Content() {

        ViewModel(factory = { PlaylistVM(getTrackUseCase) }) { viewModel ->
            val state = viewModel.uiState.collectAsState()
            val effect = viewModel.uiEffect.collectAsState(initial = null)
            val navigator = LocalNavigator.currentOrThrow

            PlaylistView(playlistState = state.value) {
                viewModel.handleAction(it)
            }

            when (effect.value) {
                is OpenMusicPlayerScreen -> navigator.push(
                    MusicPlayerScreen(
                        (effect.value as OpenMusicPlayerScreen).id,
                        getTrackUseCase
                    )
                )
            }

            LaunchedEffect(Unit) {
                viewModel.handleAction(FetchTrackData())
            }
        }
    }
}

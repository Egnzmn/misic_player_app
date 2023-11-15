package com.example.music_player_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cafe.adriel.voyager.navigator.Navigator
import com.example.music_player_app.domain.use_case.GetTrackUseCase
import com.example.music_player_app.player.MediaExoPlayer
import com.example.music_player_app.ui.screen.playlist_screen.PlaylistScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var getTrackUseCase: GetTrackUseCase
    @Inject
    lateinit var player: MediaExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigator(screen = PlaylistScreen(getTrackUseCase))
        }
    }
}

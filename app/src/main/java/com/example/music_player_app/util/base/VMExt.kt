package com.example.music_player_app.util.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.remember

@Composable
fun <T : MVIViewModel> ViewModel(
    factory: @DisallowComposableCalls () -> T,
    content: @Composable (T) -> Unit
) {
    val viewModel = remember { factory() }
    content(viewModel)
}

interface MVIViewModel

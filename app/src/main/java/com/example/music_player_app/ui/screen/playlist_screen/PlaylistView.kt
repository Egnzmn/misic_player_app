package com.example.music_player_app.ui.screen.playlist_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.music_player_app.domain.model.TrackModel
import com.example.music_player_app.ui.screen.playlist_screen.PlaylistAction.OnTrackItemClicked
import com.example.music_player_app.ui.theme.PlaylistBackgroundColor
import com.example.music_player_app.util.base.UIAction

@Composable
fun PlaylistView(
    playlistState: PlaylistState,
    actionHandler: (UIAction) -> Unit
) {
    Column {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Magenta,
                            Color.Cyan
                        )
                    )
                )
        ) {
            items(playlistState.data) {
                PlaylistItem(
                    track = it,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            actionHandler.invoke(OnTrackItemClicked(it.id))
                        }
                )
            }
        }
    }
}

@Composable
fun PlaylistItem(
    modifier: Modifier = Modifier,
    track: TrackModel,
) {
    Card(
        modifier = modifier,
        backgroundColor = PlaylistBackgroundColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            TrackCoverIcon(track)
            TrackInformation(track)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TrackCoverIcon(
    track: TrackModel,
    modifier: Modifier = Modifier,
) {
    GlideImage(
        modifier = modifier
            .size(68.dp)
            .padding(8.dp)
            .clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop,
        model = track.albumImage,
        contentDescription = null
    )
}

@Composable
fun TrackInformation(
    track: TrackModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = track.artistName,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = track.name,
            style = TextStyle(fontSize = 20.sp)
        )
    }
}

@Composable
private fun UserItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = "Get contact information",
            tint = MaterialTheme.colors.secondary
        )
    }
}

package com.example.music_player_app.ui.screen.music_player_screen

import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.music_player_app.ui.screen.music_player_screen.MusicPlayerAction.OnBackButtonClicked
import com.example.music_player_app.ui.screen.music_player_screen.MusicPlayerAction.OnDisableRepeatModeClicked
import com.example.music_player_app.ui.screen.music_player_screen.MusicPlayerAction.OnNextTrackClicked
import com.example.music_player_app.ui.screen.music_player_screen.MusicPlayerAction.OnPauseButtonClicked
import com.example.music_player_app.ui.screen.music_player_screen.MusicPlayerAction.OnPlayButtonClicked
import com.example.music_player_app.ui.screen.music_player_screen.MusicPlayerAction.OnPreviousTrackClicked
import com.example.music_player_app.ui.screen.music_player_screen.MusicPlayerAction.OnEnableRepeatModeClicked
import com.example.music_player_app.ui.screen.music_player_screen.MusicPlayerAction.OnSeekBackClicked
import com.example.music_player_app.ui.screen.music_player_screen.MusicPlayerAction.OnSeekForwardClicked
import com.example.music_player_app.ui.screen.music_player_screen.MusicPlayerAction.SeekToPosition
import com.example.music_player_app.ui.screen.music_player_screen.MusicPlayerAction.UpdateCurrentPosition
import com.example.music_player_app.util.base.UIAction
import java.time.Duration
import kotlin.time.DurationUnit.MILLISECONDS
import kotlin.time.DurationUnit.SECONDS
import kotlin.time.toDuration

@RequiresApi(VERSION_CODES.O)
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MusicPlayerView(
    musicPlayerState: MusicPlayerState,
    actionHandler: (UIAction) -> Unit
) {

    val seconds = musicPlayerState.data.duration.toDuration(SECONDS)
    val duration = seconds.toComponents { minutes, seconds, _ ->
        String.format("%02d:%02d", minutes, seconds)
    }

    val millis = musicPlayerState.currentPosition.toDuration(MILLISECONDS)
    actionHandler.invoke(UpdateCurrentPosition())
    val currentPosition = millis.toComponents { minutes, seconds, _ ->
        String.format("%02d:%02d", minutes, seconds)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Magenta,
                        Color.Cyan,
                    )
                )
            )
            .padding(horizontal = 10.dp)
    ) {
        TopBar(actionHandler = actionHandler)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(30.dp))
            GlideImage(
                model = musicPlayerState.data.albumImage,
                contentDescription = "Song banner",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .sizeIn(maxWidth = 500.dp, maxHeight = 500.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .weight(10f)
            )
            Spacer(modifier = Modifier.height(30.dp))
            TrackDescription(musicPlayerState.data.name, musicPlayerState.data.artistName)
            Spacer(modifier = Modifier.height(35.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(10f)
            ) {
                PlayerSlider(
                    actionHandler = actionHandler,
                    ofHours = Duration.ofHours(2),
                    trackDuration = duration,
                    currentPosition = currentPosition,
                    progress = musicPlayerState.progress ?: 0f
                )
                Spacer(modifier = Modifier.height(40.dp))
                PlayerButtonsBlock(
                    modifier = Modifier.padding(vertical = 8.dp),
                    musicPlayerState = musicPlayerState,
                    actionHandler = actionHandler
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun TopBar(actionHandler: (UIAction) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = { actionHandler.invoke(OnBackButtonClicked())}) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back Arrow",
                tint = Color.White
            )
        }
    }
}

@Composable
fun TrackDescription(title: String, name: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.h5,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = Color.White,
        fontWeight = FontWeight.Bold
    )

    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(
            text = name,
            style = MaterialTheme.typography.body2,
            maxLines = 1,
            color = Color.White
        )
    }
}

@Composable
fun PlayerSlider(
    actionHandler: (UIAction) -> Unit,
    ofHours: Duration?,
    trackDuration: String,
    currentPosition: String?,
    progress: Float
) {
    if (ofHours != null) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Slider(
                value = progress,
                onValueChange = { actionHandler.invoke(SeekToPosition(it)) },
                valueRange = 0f..100f,
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.White
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = currentPosition ?: "", color = Color.White)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = trackDuration, color = Color.White)
            }
        }
    }
}

@Composable
fun PlayerButtonsBlock(
    modifier: Modifier = Modifier,
    playerButtonSize: Dp = 72.dp,
    sideButtonSize: Dp = 42.dp,
    musicPlayerState: MusicPlayerState,
    actionHandler: (UIAction) -> Unit
) {

    val buttonModifier = Modifier
        .size(sideButtonSize)
        .semantics { role = Role.Button }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        Image(
            imageVector = Icons.Filled.SkipPrevious,
            contentDescription = "Skip Previous",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = buttonModifier.clickable {
                actionHandler.invoke(
                    OnPreviousTrackClicked(
                        musicPlayerState.data.id,
                        musicPlayerState.data.audio
                    )
                )
            }
        )

        Image(
            imageVector = Icons.Filled.Replay10,
            contentDescription = "Reply 10 Second",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = buttonModifier.clickable { actionHandler.invoke(OnSeekBackClicked()) }
        )

        Image(
            imageVector = if (musicPlayerState.isPlaying) Icons.Filled.PauseCircleFilled else Icons.Filled.PlayCircleFilled,
            contentDescription = "Play or pause",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .size(playerButtonSize)
                .semantics { role = Role.Button }
                .clickable {
                    if (musicPlayerState.isPlaying) actionHandler.invoke(OnPauseButtonClicked()) else
                        actionHandler.invoke(OnPlayButtonClicked(musicPlayerState.data.audio, musicPlayerState.data.id))
                }
        )

        Image(
            imageVector = Icons.Filled.Forward10,
            contentDescription = "Forward 10 Seconds",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = buttonModifier.clickable { actionHandler.invoke(OnSeekForwardClicked()) }
        )

        Image(
            imageVector = Icons.Filled.SkipNext,
            contentDescription = "Skip Next",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = buttonModifier.clickable {
                actionHandler.invoke(
                    OnNextTrackClicked(
                        musicPlayerState.data.id
                    )
                )
            }
        )
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {

        Image(
            imageVector = if (!musicPlayerState.isRepeatModeOn) Icons.Filled.Repeat else Icons.Filled.RepeatOn,
            contentDescription = "Repeat",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = buttonModifier.clickable {
                if (!musicPlayerState.isRepeatModeOn) actionHandler.invoke(
                    OnEnableRepeatModeClicked()
                ) else actionHandler.invoke(OnDisableRepeatModeClicked())
            }
        )
    }
}

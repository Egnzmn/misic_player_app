package com.example.music_player_app.player

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MediaExoPlayerImpl(
    private val context: Context
) : MediaExoPlayer {

    var player: ExoPlayer? = null

    override fun initPlayer() {
        player = ExoPlayer.Builder(context).build()
    }

    override fun deInitPlayer() {
        player?.release()
    }

    override fun startPlayer(url: String) {
        val mediaItem = MediaItem.fromUri(url)
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.playWhenReady = true
    }

    override fun pausePlayer() {
        player?.playWhenReady = false
    }

    override fun updateCurrentPosition(): Flow<Long> {
        return flow {
            while (true) {
                emit(player?.currentPosition ?: 0L)
                delay(1000L)
            }
        }
    }

    override fun getCurrentDuration(): Long {
        return player?.duration ?: 0L
    }

    override fun seekToPosition(value: Float) {
        player?.seekTo(((player?.duration?.times(value) ?: 0f) / 100f).toLong())
    }

    override fun resume() {
        player?.playWhenReady = true
    }

    override fun seekFroward() {
        player?.seekForward()
    }

    override fun seekBack() {
        player?.seekBack()
    }

    override fun getPlaybackState(): Int {
        return player?.playbackState ?: 0
    }

    override fun getIsPlayingStatus(): Boolean {
        return player?.isPlaying ?: false
    }

    override fun enableRepeatMode() {
        player?.repeatMode = Player.REPEAT_MODE_ONE
    }

    override fun disableRepeatMode() {
        player?.repeatMode = Player.REPEAT_MODE_OFF
    }
}

interface MediaExoPlayer {

    fun initPlayer()
    fun deInitPlayer()
    fun startPlayer(url: String)
    fun pausePlayer()
    fun updateCurrentPosition(): Flow<Long>
    fun getCurrentDuration(): Long
    fun seekToPosition(value: Float)
    fun resume()
    fun seekFroward()
    fun seekBack()
    fun getPlaybackState(): Int
    fun getIsPlayingStatus(): Boolean
    fun enableRepeatMode()
    fun disableRepeatMode()
}

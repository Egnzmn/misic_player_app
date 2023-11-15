package com.example.music_player_app.di

import android.content.Context
import com.example.music_player_app.data.api.JamendoApi
import com.example.music_player_app.data.db.TracksDao
import com.example.music_player_app.data.repository.TracksRepositoryImpl
import com.example.music_player_app.domain.repository.TracksRepository
import com.example.music_player_app.domain.use_case.GetTrackInfoUseCase
import com.example.music_player_app.domain.use_case.GetTrackUseCase
import com.example.music_player_app.domain.use_case.GetTracksIDsUseCase
import com.example.music_player_app.player.MediaExoPlayer
import com.example.music_player_app.player.MediaExoPlayerImpl
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.audio.AudioAttributes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideExoPlayer(@ApplicationContext context: Context): MediaExoPlayer = MediaExoPlayerImpl(context)

    @Provides
    fun provideAudioAttributes(): AudioAttributes =
        AudioAttributes.Builder()
            .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
            .setUsage(C.USAGE_MEDIA)
            .build()

    @Provides
    @Singleton
    fun provideTrackRepository(
        jamendoApi: JamendoApi,
        tracksDao: TracksDao
    ): TracksRepository {
        return TracksRepositoryImpl(jamendoApi, tracksDao)
    }

    @Provides
    @Singleton
    fun provideGetTackInfoUseCase(repo: TracksRepository): GetTrackInfoUseCase {
        return GetTrackInfoUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideGetTracksIDsUseCase(repo: TracksRepository): GetTracksIDsUseCase {
        return GetTracksIDsUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideGetTrackUseCase(repo: TracksRepository): GetTrackUseCase {
        return GetTrackUseCase(repo)
    }
}

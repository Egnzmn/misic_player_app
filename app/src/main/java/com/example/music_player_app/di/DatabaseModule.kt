package com.example.music_player_app.di

import android.content.Context
import androidx.room.Room
import com.example.music_player_app.data.db.TracksDB
import com.example.music_player_app.data.db.TracksDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMoviesDB(@ApplicationContext context: Context): TracksDB = Room.databaseBuilder(
        context,
        TracksDB::class.java,
        "MoviesDB"
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideTracksDao(tracksDb: TracksDB): TracksDao = tracksDb.tracksDao()
}

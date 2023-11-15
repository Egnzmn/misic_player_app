package com.example.music_player_app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.music_player_app.data.db.entity.TracksEntity

@Database(
    entities = [TracksEntity::class],
    version = 1
)
abstract class TracksDB : RoomDatabase() {

    abstract fun tracksDao(): TracksDao
}

package com.example.music_player_app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.music_player_app.data.db.entity.TracksEntity

@Dao
interface TracksDao {

    @Query("SELECT id FROM track_table")
    suspend fun getTracksIDs(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTracks(tracks: List<TracksEntity>)
}

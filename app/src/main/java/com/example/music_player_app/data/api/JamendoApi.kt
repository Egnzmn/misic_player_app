package com.example.music_player_app.data.api

import com.example.music_player_app.data.TracksDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface JamendoApi {

    @GET("tracks/")
    suspend fun getTracks(
        @Query("client_id") client_id: String = "e1bef7d5"
    ): TracksDataResponse

    @GET("tracks/")
    suspend fun getTrackInfo(
        @Query("client_id") client_id: String = "e1bef7d5",
        @Query("id") id: String
    ): TracksDataResponse
}

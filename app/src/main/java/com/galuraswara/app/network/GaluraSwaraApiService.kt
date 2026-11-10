package com.galuraswara.app.network

import com.galuraswara.app.model.ApiResponse
import com.galuraswara.app.model.Playlist
import com.galuraswara.app.model.PlaylistDetail
import com.galuraswara.app.model.Song
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface GaluraSwaraApiService {

    @GET("/api/songs")
    suspend fun getSongs(
        @Query("search") search: String? = null,
        @Query("genre") genre: String? = null,
        @Query("artist") artist: String? = null
    ): ApiResponse<List<Song>>

    @GET("/api/songs/{id}")
    suspend fun getSongById(@Path("id") id: String): ApiResponse<Song>

    @GET("/api/playlists")
    suspend fun getPlaylists(): ApiResponse<List<Playlist>>

    @GET("/api/playlists/{id}")
    suspend fun getPlaylistById(@Path("id") id: String): ApiResponse<PlaylistDetail>
}

object NetworkClient {
    // Alamat default localhost emulator Android (10.0.2.2) atau domain Alwaysdata produksi
    private const val BASE_URL = "https://galuraswara.alwaysdata.net/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    val apiService: GaluraSwaraApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GaluraSwaraApiService::class.java)
    }
}

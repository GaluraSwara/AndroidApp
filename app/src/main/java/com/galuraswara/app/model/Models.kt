package com.galuraswara.app.model

import com.google.gson.annotations.SerializedName

/**
 * Model data lagu tradisional
 */
data class Song(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("title")
    val title: String,
    @SerializedName("artist")
    val artist: String,
    @SerializedName("album")
    val album: String = "",
    @SerializedName("genre")
    val genre: String = "",
    @SerializedName("duration")
    val duration: Int = 0,
    @SerializedName("cover_url")
    val coverUrl: String = "",
    @SerializedName("audio_url")
    val audioUrl: String
)

/**
 * Model data playlist
 */
data class Playlist(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String = "",
    @SerializedName("cover_url")
    val coverUrl: String = "",
    @SerializedName("user_id")
    val userId: String = "",
    @SerializedName("songs")
    val songs: List<String> = emptyList(),
    @SerializedName("is_public")
    val isPublic: Boolean = true
)

/**
 * Model detail playlist beserta daftar lagunya
 */
data class PlaylistDetail(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String = "",
    @SerializedName("cover_url")
    val coverUrl: String = "",
    @SerializedName("user_id")
    val userId: String = "",
    @SerializedName("songs")
    val songs: List<Song> = emptyList(),
    @SerializedName("is_public")
    val isPublic: Boolean = true
)

/**
 * Struktur Base Response API
 */
data class ApiResponse<T>(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: T? = null
)

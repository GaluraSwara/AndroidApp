package com.galuraswara.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.galuraswara.app.model.Playlist
import com.galuraswara.app.model.Song
import com.galuraswara.app.player.MusicPlayerController
import com.galuraswara.app.ui.theme.*

class MainActivity : ComponentActivity() {

    private lateinit var playerController: MusicPlayerController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playerController = MusicPlayerController(this)

        setContent {
            GaluraSwaraTheme {
                MainAppScreen(playerController)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        playerController.release()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen(player: MusicPlayerController) {
    var selectedTab by remember { mutableStateOf(0) }

    // Contoh data lagu tradisional
    val sampleSongs = remember {
        listOf(
            Song(
                id = "1",
                title = "Es Lilin",
                artist = "Nining Meida",
                album = "Koleksi Tembang Sunda",
                genre = "Pop Sunda",
                duration = 215,
                audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
            ),
            Song(
                id = "2",
                title = "Manuk Dadali",
                artist = "Sambas Mangundikarta",
                album = "Lagu Daerah Jawa Barat",
                genre = "Degung Sunda",
                duration = 180,
                audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3"
            ),
            Song(
                id = "3",
                title = "Paturay Tineung",
                artist = "Mang Koko",
                album = "Rampak Sekar Sunda",
                genre = "Karawitan",
                duration = 240,
                audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3"
            ),
            Song(
                id = "4",
                title = "Gugur Bunga Gamelan",
                artist = "Swara Nusantara",
                album = "Instrumental Nusantara",
                genre = "Gamelan",
                duration = 210,
                audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3"
            )
        )
    }

    val samplePlaylists = remember {
        listOf(
            Playlist(id = "p1", name = "Degung Pasundan Pengantar Istirahat", description = "Alunan gamelan degung Sunda yang damai nan syahdu"),
            Playlist(id = "p2", name = "Kecapi Suling Parahyangan", description = "Alunan tiupan suling bambu dan petikan senar kecapi"),
            Playlist(id = "p3", name = "Gamelan Jawa Luhur", description = "Komposisi gending Jawa pengiring ketenangan batin")
        )
    }

    val currentSong by player.currentSong.collectAsState()
    val isPlaying by player.isPlaying.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(text = "🎵", fontSize = 22.sp)
                        Column {
                            Text(text = "GaluraSwara", fontWeight = FontWeight.Bold, fontSize = 19.sp, color = Color.White)
                            Text(text = "Swara Budaya Nusantara", fontSize = 11.sp, color = BrassAccent)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = WoodDark
                )
            )
        },
        bottomBar = {
            Column {
                // Mini Player Bar jika ada lagu yang dimainkan
                currentSong?.let { song ->
                    MiniPlayerBar(
                        song = song,
                        isPlaying = isPlaying,
                        onPlayPauseClick = { player.togglePlayPause() }
                    )
                }

                // Navigasi Bawah
                NavigationBar(containerColor = WoodDark) {
                    NavigationBarItem(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        icon = { Icon(Icons.Default.MusicNote, contentDescription = "Musik", tint = if (selectedTab == 0) BrassAccent else Color.Gray) },
                        label = { Text("Musik", color = if (selectedTab == 0) BrassAccent else Color.Gray) }
                    )
                    NavigationBarItem(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        icon = { Icon(Icons.Default.PlaylistPlay, contentDescription = "Playlist", tint = if (selectedTab == 1) BrassAccent else Color.Gray) },
                        label = { Text("Playlist", color = if (selectedTab == 1) BrassAccent else Color.Gray) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(CreamBackground)
        ) {
            if (selectedTab == 0) {
                SongListSection(
                    songs = sampleSongs,
                    onSongClick = { song -> player.playSong(song) }
                )
            } else {
                PlaylistSection(playlists = samplePlaylists)
            }
        }
    }
}

@Composable
fun SongListSection(songs: List<Song>, onSongClick: (Song) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text(
                text = "Koleksi Lagu Pilihan",
                style = MaterialTheme.typography.titleLarge,
                color = WoodDark,
                modifier = Modifier.padding(bottom = 6.dp)
            )
        }
        items(songs) { song ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSongClick(song) },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(WoodPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🪕", fontSize = 22.sp)
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = song.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = WoodDark,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${song.artist} • ${song.genre}",
                            fontSize = 12.sp,
                            color = TextMuted,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    IconButton(onClick = { onSongClick(song) }) {
                        Icon(
                            imageVector = Icons.Default.PlayCircle,
                            contentDescription = "Putar",
                            tint = BrassAccent,
                            modifier = Modifier.size(34.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PlaylistSection(playlists: List<Playlist>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Daftar Putar Tradisional",
                style = MaterialTheme.typography.titleLarge,
                color = WoodDark,
                modifier = Modifier.padding(bottom = 6.dp)
            )
        }
        items(playlists) { playlist ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(BrassAccent),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🎼", fontSize = 26.sp)
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = playlist.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = WoodDark
                        )
                        Text(
                            text = playlist.description,
                            fontSize = 12.sp,
                            color = TextMuted,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MiniPlayerBar(song: Song, isPlaying: Boolean, onPlayPauseClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = WoodPrimary,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(BrassAccent),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "🎵", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = song.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = song.artist,
                    fontSize = 11.sp,
                    color = BrassAccent,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconButton(onClick = onPlayPauseClick) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.PauseCircle else Icons.Default.PlayCircle,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = BrassLight,
                    modifier = Modifier.size(38.dp)
                )
            }
        }
    }
}

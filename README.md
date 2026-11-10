# 🎵 GaluraSwara AndroidApp

Aplikasi Android Native Kotlin untuk memutar musik dan playlist bertema tradisional Sunda dan Nusantara.

- 🌐 **Landing Page**: [https://galuraswara.github.io/](https://galuraswara.github.io/)
- 📄 **API Docs**: [https://galuraswara.github.io/apidocs/](https://galuraswara.github.io/apidocs/)
- ⚙️ **Backend**: [GaluraSwara/Backend](https://github.com/GaluraSwara/Backend)

---

## 🚀 Fitur Aplikasi
- **Native Kotlin & Jetpack Compose**: Antarmuka modern, responsif, dan hemat daya.
- **Audio Engine Media3 (ExoPlayer)**: Playback musik stabil di background, mini player bar interaktif, serta kontrol audio lengkap.
- **Tema Visual Tradisional**: Palet warna kayu jati tua, kuningan emas, dan nuansa khas Nusantara.
- **Katalog Lagu & Playlist**: Mendukung pemutaran lagu daerah (Degung Sunda, Tembang Pasundan, Gamelan Jawa, dll).
- **Integrasi REST API**: Terhubung ke Backend GaluraSwara (Go Fiber & MongoDB).
- **Auto Tag & Auto Release APK**: Otomatis membangun paket binary `.apk`, membuat git tag versi baru (`v1.0.x`), dan mempublikasikan ke **GitHub Releases** setiap ada push ke branch `main`/`master`.

---

## 📁 Struktur Modul
```
AndroidApp/
├── .github/workflows/
│   └── release.yml                 # Workflow Auto Tag & Release APK
├── app/
│   ├── src/main/
│   │   ├── java/com/galuraswara/app/
│   │   │   ├── model/Models.kt            # Data model Song, Playlist, Response
│   │   │   ├── network/GaluraSwaraApiService.kt # Retrofit REST Client
│   │   │   ├── player/MusicPlayerController.kt  # Media3 ExoPlayer Engine
│   │   │   ├── ui/theme/                  # Tema etnik Sunda/Nusantara
│   │   │   └── MainActivity.kt            # UI Compose & Screen Navigation
│   │   ├── res/                           # Aset Drawable, Colors, Manifest
│   │   └── AndroidManifest.xml
│   ├── build.gradle.kts                   # Dependensi modul App
│   └── proguard-rules.pro
├── gradle/
│   ├── libs.versions.toml                 # Version Catalog dependensi
│   └── wrapper/
│       └── gradle-wrapper.properties      # Konfigurasi Gradle Wrapper 8.9
├── build.gradle.kts                       # Root build configuration
├── settings.gradle.kts                    # Root settings
├── gradle.properties
└── README.md
```

---

## 🛠️ Kompilasi & Build Lokal

Untuk membuka dan menjalankan aplikasi di komputer Anda:
1. Buka folder `AndroidApp` menggunakan **Android Studio (Ladybug / Koala atau lebih baru)**.
2. Tunggu sinkronisasi Gradle (menggunakan JDK 17).
3. Jalankan ke perangkat fisik Android atau Emulator:
   ```bash
   ./gradlew assembleDebug
   ```
4. Untuk build release:
   ```bash
   ./gradlew assembleRelease
   ```

---

## 🤖 CI/CD Auto Tag & Auto Release APK
Setiap commit yang di-push ke branch `main` atau `master` pada GitHub akan memicu workflow `.github/workflows/release.yml`:
1. Mengkompilasi APK release secara otomatis di cloud runner GitHub Actions.
2. Membuat tag versi otomatis (contoh: `v1.0.1`, `v1.0.2`, dst).
3. Mengunggah file `GaluraSwara-v1.0.x.apk` langsung ke halaman GitHub Releases.
4. Landing page di `galuraswara.github.io` akan otomatis mendeteksi rilis ini dan menyediakan link unduh langsung bagi pengguna.

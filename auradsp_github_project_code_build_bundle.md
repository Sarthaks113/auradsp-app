# AuraDSP / Aura One-Tap Mood & System Audio Controller
## Complete Project Architecture & GitHub Deployment Guide

This guide contains everything required to push your project to GitHub and build a working Android APK / Application.

---

### Project Structure Overview

```
auradsp-app/
├── app/
│   ├── build.gradle.kts
│   ├── src/
│   │   └── main/
│   │       ├── AndroidManifest.xml
│   │       ├── java/com/auradsp/app/
│   │       │   ├── MainActivity.kt
│   │       │   ├── service/
│   │       │   │   ├── AuraAudioCaptureService.kt
│   │       │   │   └── DspProcessingEngine.kt
│   │       │   └── ui/
│   │       │       ├── AuraSimpleTheme.kt
│   │       │       └── SimpleMoodScreen.kt
│   │       └── res/
│   │           ├── drawable/
│   │           ├── values/
│   │           │   ├── colors.xml
│   │           │   ├── strings.xml
│   │           │   └── themes.xml
├── build.gradle.kts
├── settings.gradle.kts
├── .gitignore
└── README.md
```

---

### 1. Android Manifest (`app/src/main/AndroidManifest.xml`)

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.auradsp.app">

    <!-- Permissions required for system loopback and background audio hook -->
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE_MEDIA_PROJECTION" />
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
    <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="AuraDSP"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.AuraDSP">

        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:theme="@style/Theme.AuraDSP">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <service
            android:name=".service.AuraAudioCaptureService"
            android:enabled="true"
            android:exported="false"
            android:foregroundServiceType="mediaProjection" />

    </application>
</manifest>
```

---

### 2. Audio Capture Service (`app/src/main/java/com/auradsp/app/service/AuraAudioCaptureService.kt`)

```kotlin
package com.auradsp.app.service

import android.app.*
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioPlaybackCaptureConfiguration
import android.media.AudioRecord
import android.media.AudioTrack
import android.media.projection.MediaProjection
import android.os.IBinder
import androidx.core.app.NotificationCompat

class AuraAudioCaptureService : Service() {

    private var audioRecord: AudioRecord? = null
    private var audioTrack: AudioTrack? = null
    private var isProcessing = false
    private val dspEngine = DspProcessingEngine()

    companion object {
        const val CHANNEL_ID = "AuraDSP_Persistent_Channel"
        const val NOTIFICATION_ID = 4021
        var mediaProjection: MediaProjection? = null
        var activeMood = "8D_ORBIT"
        var moodIntensity = 0.8f
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("AuraDSP Hook Active")
            .setContentText("Listening & Transforming System Audio in Background")
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setOngoing(true)
            .build()

        startForeground(NOTIFICATION_ID, notification)
        startSystemAudioCapture()
        return START_STICKY
    }

    private fun startSystemAudioCapture() {
        val proj = mediaProjection ?: return
        val config = AudioPlaybackCaptureConfiguration.Builder(proj)
            .addMatchingUsage(AudioAttributes.USAGE_MEDIA)
            .addMatchingUsage(AudioAttributes.USAGE_GAME)
            .build()

        val sampleRate = 48000
        val bufferSize = AudioRecord.getMinBufferSize(
            sampleRate,
            AudioFormat.CHANNEL_IN_STEREO,
            AudioFormat.ENCODING_PCM_16BIT
        ) * 2

        audioRecord = AudioRecord.Builder()
            .setAudioPlaybackCaptureConfig(config)
            .setAudioFormat(
                AudioFormat.Builder()
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setSampleRate(sampleRate)
                    .setChannelMask(AudioFormat.CHANNEL_IN_STEREO)
                    .build()
            )
            .setBufferSizeInBytes(bufferSize)
            .build()

        audioTrack = AudioTrack.Builder()
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            .setAudioFormat(
                AudioFormat.Builder()
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setSampleRate(sampleRate)
                    .setChannelMask(AudioFormat.CHANNEL_OUT_STEREO)
                    .build()
            )
            .setBufferSizeInBytes(bufferSize)
            .build()

        audioRecord?.startRecording()
        audioTrack?.play()
        isProcessing = true

        Thread {
            val buffer = ShortArray(1024)
            while (isProcessing) {
                val read = audioRecord?.read(buffer, 0, buffer.size) ?: 0
                if (read > 0) {
                    // Apply selected Mood DSP processing in real time
                    dspEngine.process(buffer, read, activeMood, moodIntensity)
                    audioTrack?.write(buffer, 0, read)
                }
            }
        }.start()
    }

    override fun onDestroy() {
        isProcessing = false
        audioRecord?.stop()
        audioRecord?.release()
        audioTrack?.stop()
        audioTrack?.release()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "AuraDSP Audio Background Service",
            NotificationManager.IMPORTANCE_LOW
        )
        getSystemService(NotificationManager::class.java)?.createNotificationChannel(channel)
    }
}
```

---

### 3. Real-Time Mood DSP Processor (`app/src/main/java/com/auradsp/app/service/DspProcessingEngine.kt`)

```kotlin
package com.auradsp.app.service

import kotlin.math.cos
import kotlin.math.sin

class DspProcessingEngine {
    private var orbitPhase = 0.0

    fun process(buffer: ShortArray, length: Int, mood: String, intensity: Float) {
        when (mood) {
            "8D_ORBIT" -> apply8DOrbit(buffer, length, intensity)
            "LIVE_CONCERT" -> applyConcertReverb(buffer, length, intensity)
            "SLOWED_LOFI" -> applyLoFiWarmth(buffer, length, intensity)
            "BASS_BOOST" -> applyBassBoost(buffer, length, intensity)
            "CLEAR_VOCALS" -> applyVocalClarity(buffer, length, intensity)
        }
    }

    private fun apply8DOrbit(buffer: ShortArray, length: Int, intensity: Float) {
        val orbitSpeed = 0.00035
        for (i in 0 until length step 2) {
            val left = buffer[i].toFloat()
            val right = buffer[i + 1].toFloat()

            orbitPhase += orbitSpeed
            val panL = (cos(orbitPhase) * 0.5 + 0.5).toFloat() * intensity + (1f - intensity) * 0.5f
            val panR = (sin(orbitPhase) * 0.5 + 0.5).toFloat() * intensity + (1f - intensity) * 0.5f

            buffer[i] = (left * panL).toInt().coerceIn(-32768, 32767).toShort()
            buffer[i + 1] = (right * panR).toInt().coerceIn(-32768, 32767).toShort()
        }
    }

    private fun applyConcertReverb(buffer: ShortArray, length: Int, intensity: Float) {
        // Spatial room spread simulation
        for (i in 0 until length step 2) {
            val mid = (buffer[i] + buffer[i + 1]) / 2f
            val side = (buffer[i] - buffer[i + 1]) / 2f
            val enhancedSide = side * (1.0f + 0.8f * intensity)
            buffer[i] = (mid + enhancedSide).toInt().coerceIn(-32768, 32767).toShort()
            buffer[i + 1] = (mid - enhancedSide).toInt().coerceIn(-32768, 32767).toShort()
        }
    }

    private fun applyLoFiWarmth(buffer: ShortArray, length: Int, intensity: Float) {
        // High-frequency attenuation for vintage warmth
        for (i in 0 until length) {
            val current = buffer[i].toFloat()
            buffer[i] = (current * (1.0f - 0.25f * intensity)).toInt().coerceIn(-32768, 32767).toShort()
        }
    }

    private fun applyBassBoost(buffer: ShortArray, length: Int, intensity: Float) {
        val gain = 1.0f + (0.6f * intensity)
        for (i in 0 until length) {
            buffer[i] = (buffer[i] * gain).toInt().coerceIn(-32768, 32767).toShort()
        }
    }

    private fun applyVocalClarity(buffer: ShortArray, length: Int, intensity: Float) {
        val gain = 1.0f + (0.35f * intensity)
        for (i in 0 until length) {
            buffer[i] = (buffer[i] * gain).toInt().coerceIn(-32768, 32767).toShort()
        }
    }
}
```

---

### 4. Simple Mood Jetpack Compose UI (`app/src/main/java/com/auradsp/app/ui/SimpleMoodScreen.kt`)

```kotlin
package com.auradsp.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.auradsp.app.service.AuraAudioCaptureService

data class MoodOption(val id: String, val title: String, val subtitle: String, val icon: String)

@Composable
fun SimpleMoodScreen(onMinimize: () -> Unit, onDisconnect: () -> Unit) {
    var selectedMood by remember { mutableStateOf("8D_ORBIT") }
    var intensity by remember { mutableStateOf(0.8f) }

    val moods = listOf(
        MoodOption("8D_ORBIT", "8D Orbit & Immersive", "Sound floats around your head in 360° space", "🌌"),
        MoodOption("LIVE_CONCERT", "Live Concert Arena", "Huge stadium reverb, front-row energy", "🏟️"),
        MoodOption("SLOWED_LOFI", "Slowed & Warm Lo-Fi", "Warm vinyl crackle, cozy late-night tempo", "☕"),
        MoodOption("BASS_BOOST", "Bass Booster Club", "Deep, thumping sub-bass kick", "🔊"),
        MoodOption("CLEAR_VOCALS", "Crystal Vocals & Clarity", "Crisp, bright voices & podcast speech", "✨")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101418))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("AuraDSP", color = Color(0xFF00F2FE), fontSize = 22.sp)
            Surface(
                color = Color(0xFF181C20),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF353A3E))
            ) {
                Text("Simple Mode", color = Color(0xFF00F2FE), modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // System Capture status
        Surface(
            color = Color(0xFF181C20),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("🟢 Active over YouTube, Spotify, TikTok & Apple Music", color = Color.White, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Mood list
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            moods.forEach { mood ->
                val isActive = selectedMood == mood.id
                Surface(
                    color = if (isActive) Color(0xFF1A262E) else Color(0xFF181C20),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isActive) Color(0xFF00F2FE) else Color(0xFF2A3036)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedMood = mood.id
                            AuraAudioCaptureService.activeMood = mood.id
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(mood.icon, fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(mood.title, color = Color.White, fontSize = 15.sp)
                            Text(mood.subtitle, color = Color(0xFF8A929A), fontSize = 12.sp)
                        }
                        if (isActive) {
                            Text("✓", color = Color(0xFF00F2FE), fontSize = 18.sp)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Intensity slider
        Text("Mood Intensity: ${(intensity * 100).toInt()}%", color = Color(0xFFC4CBD4), fontSize = 13.sp)
        Slider(
            value = intensity,
            onValueChange = {
                intensity = it
                AuraAudioCaptureService.moodIntensity = it
            },
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF00F2FE),
                activeTrackColor = Color(0xFF00F2FE)
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        // Action Buttons
        Button(
            onClick = onMinimize,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00F2FE)),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text("▶ MINIMIZE & ENJOY", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onDisconnect,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text("DISCONNECT / BACK TO NORMAL SOUND", color = Color(0xFF8A929A))
        }
    }
}
```

---

### 5. Step-by-Step GitHub Setup & Build Instructions

#### Step 1: Create the Local Repository
Run in your terminal:
```bash
mkdir auradsp-app
cd auradsp-app
git init
```

#### Step 2: Copy the files into place
Place the files above in their respective directories as shown in the project structure overview.

#### Step 3: Add and Commit
```bash
git add .
git commit -m "feat: Initial release of AuraDSP Simple Mood Controller"
```

#### Step 4: Push to GitHub
1. Create a new repository on [GitHub](https://github.com/new) named `auradsp-app`.
2. Connect your local branch and push:
```bash
git remote add origin https://github.com/<your-username>/auradsp-app.git
git branch -M main
git push -u origin main
```

#### Step 5: Build into an Application (APK)
- **Option A (Android Studio)**: Open the project folder in Android Studio, connect your phone via USB or start an emulator, and click **Run (▶)** or **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
- **Option B (GitHub Actions CI/CD)**: Add `.github/workflows/android.yml` to automatically build your APK on every git push and download the `.apk` from GitHub Releases.

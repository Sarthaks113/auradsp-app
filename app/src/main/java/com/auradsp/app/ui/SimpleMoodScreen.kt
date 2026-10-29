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

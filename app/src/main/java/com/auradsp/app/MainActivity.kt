package com.auradsp.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import com.auradsp.app.service.AuraAudioCaptureService
import com.auradsp.app.ui.SimpleMoodScreen

class MainActivity : ComponentActivity() {

    private lateinit var mediaProjectionManager: MediaProjectionManager

    private val captureLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val proj = mediaProjectionManager.getMediaProjection(result.resultCode, result.data!!)
            AuraAudioCaptureService.mediaProjection = proj
            
            val intent = Intent(this, AuraAudioCaptureService::class.java)
            startForegroundService(intent)
            Toast.makeText(this, "AuraDSP Hook Active!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Permission denied for system audio", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mediaProjectionManager = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        captureLauncher.launch(mediaProjectionManager.createScreenCaptureIntent())

        setContent {
            SimpleMoodScreen(
                onMinimize = {
                    moveTaskToBack(true)
                },
                onDisconnect = {
                    stopService(Intent(this, AuraAudioCaptureService::class.java))
                    Toast.makeText(this, "AuraDSP Disconnected", Toast.LENGTH_SHORT).show()
                    finish()
                }
            )
        }
    }
}

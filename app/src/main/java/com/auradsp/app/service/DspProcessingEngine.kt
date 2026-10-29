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

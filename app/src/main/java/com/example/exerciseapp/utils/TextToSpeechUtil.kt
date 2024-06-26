package com.example.exerciseapp.utils

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class TextToSpeechUtil(context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = TextToSpeech(context, this)
    private var isInitialized = false

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result: Int = tts?.setLanguage(Locale.US) ?: TextToSpeech.LANG_MISSING_DATA
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The language specified is not supported!")
            } else {
                isInitialized = true
            }
        } else {
            Log.e("TTS", "Initialization failed!")
        }
    }

    fun speak(text: String) {
        if (isInitialized) {
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    fun shutDown() {
        tts?.let {
            it.stop()
            it.shutdown()
        }
    }
}
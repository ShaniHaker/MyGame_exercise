package com.example.mygame_exercize.utilities

import android.media.MediaPlayer
import java.lang.ref.WeakReference
import android.content.Context


class BackgroundMusicPlayer private constructor(context: Context){

    //private constructor ensures that no external code can create instances directly
    //Singelton =>This ensures only one instance of BackgroundMusicPlayer exists throughout the app.
    //Manage background music efficiently across the app without duplicating objects

    private val contextRef = WeakReference(context)// A WeakReference allows holding a reference to an object (like Context) without preventing it from being garbage-collected
    private var mediaPlayer: MediaPlayer? = null
    //initially set to null because the MediaPlayer instance is created only when the music is played
    private var resId: Int = 0//each audio file has a unique id


    //audio contextRef: Keeps a reference to the context (used for accessing resources like the audio file).
    //mediaPlayer: Plays the audio file whose resource ID is stored in resId.
    //resId: Specifies which audio file the MediaPlayer should play

    fun setResourceId(id: Int) {
        this.resId = id
        //Sets the resource ID for the audio file to be played.
        initMediaPlayer()
    }

    private fun initMediaPlayer() {
        if (mediaPlayer != null)
            release()
        mediaPlayer = MediaPlayer.create(contextRef.get(), resId)
        mediaPlayer!!.isLooping = true
        mediaPlayer!!.setVolume(0.4f, 0.4f)
    }

    private fun release() {
        if (mediaPlayer == null)
            return
        try {
            mediaPlayer!!.release() // Release resources held by MediaPlayer
            mediaPlayer = null // Nullify the reference
        } catch (ex: IllegalStateException) {
            ex.printStackTrace()
        }
    }

    fun playMusic() {
        if (mediaPlayer == null || !mediaPlayer!!.isPlaying)//Checks if MediaPlayer is null or not playing:
        //If true, reinitializes the MediaPlayer
            initMediaPlayer()
        try {
            mediaPlayer!!.start() // Starts playback
        } catch (ex: IllegalStateException) {
            ex.printStackTrace()
        }
    }

    fun pauseMusic() {
        if (mediaPlayer == null || !mediaPlayer!!.isPlaying)//if its null or currently not playing no need to pause
            return
        try {
            mediaPlayer!!.pause() // Pauses playback
        } catch (ex: IllegalStateException) {
            ex.printStackTrace()
        }
    }


    fun stopMusic() {
        if (mediaPlayer == null)
            return
        try {
            mediaPlayer!!.stop() // Stops playback
            release() // Releases resources
        } catch (ex: IllegalStateException) {
            ex.printStackTrace()
        }
    }


    companion object {
        @Volatile
        private var instance: BackgroundMusicPlayer? = null

        fun init(context: Context): BackgroundMusicPlayer {
            return instance ?: synchronized(this) {
                instance ?: BackgroundMusicPlayer(context).also { instance = it }
            }
        }

        fun getInstance(): BackgroundMusicPlayer{
            return instance ?:
            throw IllegalStateException("BackgroundMusicPlayer must be initialized by calling init(context) before use.")
        }
    }


}
package com.example.mygame_exercize

import android.app.Application
import com.example.mygame_exercize.utilities.AlertManager
import com.example.mygame_exercize.utilities.BackgroundMusicPlayer
import com.example.mygame_exercize.utilities.SharedPreferencesManager

class MyApp : Application() {

    //This class extends Application, which means it will run when the app is launched,
    // before any activities or components are created.

    override fun onCreate() {
        super.onCreate()
        AlertManager.init(this)
        BackgroundMusicPlayer.init(this)
        //The BackgroundMusicPlayer class uses a singleton pattern, meaning only one instance of this class will exist throughout the app's lifecycle.
        // init(context) ensures that the context is set up properly before using the player.
        SharedPreferencesManager.init(this)
        BackgroundMusicPlayer.getInstance().setResourceId(R.raw.background_music)


        //When the app is launched, the MyApp class is executed, initializing BackgroundMusicPlayer with the application context.
        //The BackgroundMusicPlayer then has access to the context, allowing it to load and play the background music (R.raw.background_music)
    }
        override fun onTerminate() {
            super.onTerminate()
            BackgroundMusicPlayer.getInstance().stopMusic()

            //in the Android Emulator: When you press the "Stop" button in Android Studio or close the emulator,
            // the onTerminate() method is called
        }
    }
    
}

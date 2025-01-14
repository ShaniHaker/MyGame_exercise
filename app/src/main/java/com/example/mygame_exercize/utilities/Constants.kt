package com.example.mygame_exercize.utilities

class Constants {
    object GameLogic{

        const val INITIAL_POSTION : Int = 2//the initial index for the character position
        const val DELAY_FOR_RUNNABLE : Long = 1000L

    }

    object PlayModes {
        const val TILT = "Tilt"
        const val BUTTONS = "Buttons"

    }



    object BundleKeys {
        const val SCORE_KEY = "SCORE_KEY"
        const val SPEED_MODE_KEY = "SPEED_MODE_KEY"
        const val CONTROL_MODE_KEY = "CONTROL_MODE_KEY"
    }

    object Difficulties{

        const val MORE_DELAY : Long = 900L
        const val LESS_DELAY : Long = 500L
    }

    object SPKeys{
        const val SCORES_KEY = "SCORES_KEY"
    }

    object File{
        const val SHARED_PREF = "SHARED PREF"
    }

    object PremittionLocation {
        const val LOCATION_PERMISSION_REQUEST= 1001
    }
}
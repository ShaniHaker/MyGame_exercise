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
        const val SPEED_MODE_KEY = "SPEED_MODE_KEY"
        const val CONTROL_MODE_KEY = "CONTROL_MODE_KEY"
    }

    object Difficulties{

        const val MORE_DELAY : Long = 900L
        const val LESS_DELAY : Long = 500L
    }
}
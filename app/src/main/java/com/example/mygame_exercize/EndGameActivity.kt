package com.example.mygame_exercize

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textview.MaterialTextView

class EndGameActivity : AppCompatActivity() {

    private lateinit var end_game_text : MaterialTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_end_game)
       findViews()
       initViews()
    }

    private fun initViews() {
    //display the message of game over
    //This directly sets the TextView to show the string "Game Over".
        end_game_text.text = getString(R.string.game_over_message, "😞😞😞")
    //getString(R.string.game_over_message, emoji) retrieves the string "Game over! %1$s",
    // and replaces %1$s with the value of the emoji variable
    }

    private fun findViews() {
        end_game_text = findViewById(R.id.end_game_text)
        //connect the MaterialTextView in the layout to the variable in the code.
    }



}
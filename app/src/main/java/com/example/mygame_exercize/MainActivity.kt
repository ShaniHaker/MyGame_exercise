package com.example.mygame_exercize

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class MainActivity : AppCompatActivity() {

    //defining variables that will be initialized later
    private lateinit var rightArrow: ExtendedFloatingActionButton
    private lateinit var leftArrow: ExtendedFloatingActionButton
    private lateinit var heartsView: Array<AppCompatImageView> //array of hearts,
    // The number of hearts won't change so no need to choose Array collection
    private lateinit var charViews : Array<AppCompatImageView>//array of astronaut char
    private lateinit var astoridViews : Array<Array<AppCompatImageView>>
    private lateinit var myGameManager: SpaceGameManager

    // represent, the array of boolean for charcters that will be intilazed late
    private lateinit var charVisibility: BooleanArray



    //onCreate is the first method that runs when program starts the MainActivity,
    // it serves as the entry point for setting up my application's UI and initializing components.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Set the layout for this activity
        setContentView(R.layout.activity_main) //Load the activity layout using setContentView

        //Instead of doing everything directly inside onCreate
        //we split tasks to helper functions
        findViews()//the function
        initViews()
        myGameManager = SpaceGameManager(heartsView.size,astoridViews[0].size, astoridViews.size)
        // Initialize charVisibility array of boolean elements based on the number of character views
        charVisibility = BooleanArray(charViews.size) { false }//at first all elements are false
        charVisibility[1] = true // Make the second character visible at the start
        }

    private fun findViews() {// helper function to locate and assign views to variables= initialize

        // Initialize the arrows buttons
        rightArrow = findViewById(R.id.main_BTN_RIGHT)
        leftArrow = findViewById(R.id.main_BTN_LEFT)

        // Initialize the array of hearts
        heartsView = arrayOf(
            findViewById(R.id.main_IMG_heart1),
            findViewById(R.id.main_IMG_heart2),
            findViewById(R.id.main_IMG_heart3)
        )

        // Initialize the array of astronaut characters
        charViews = arrayOf(
            findViewById(R.id.main_astro1_img),
            findViewById(R.id.main_astro2_img),
            findViewById(R.id.main_astro3_img)
        )

        // Initialize the matrix of asteroids
        astoridViews = arrayOf(
            arrayOf(
                findViewById(R.id.main_obstacle1),
                findViewById(R.id.main_obstacle2),
                findViewById(R.id.main_obstacle3),
                findViewById(R.id.main_obstacle4),
                findViewById(R.id.main_obstacle5),
                findViewById(R.id.main_obstacle6)

            ),
            arrayOf(
                findViewById(R.id.main_obstacle8),
                findViewById(R.id.main_obstacle9),
                findViewById(R.id.main_obstacle10),
                findViewById(R.id.main_obstacle11),
                findViewById(R.id.main_obstacle12),
                findViewById(R.id.main_obstacle13)
            ),
            arrayOf(
                findViewById(R.id.main_obstacle15),
                findViewById(R.id.main_obstacle16),
                findViewById(R.id.main_obstacle17),
                findViewById(R.id.main_obstacle18),
                findViewById(R.id.main_obstacle19),
                findViewById(R.id.main_obstacle20)
            )
        )

    }
    private fun initViews() { //set up the interactive behavior for your views
    // after they have been initialized in findViews()

        // Set click listener for leftArrow
        leftArrow.setOnClickListener {
            myGameManager.moveCharacterLeft() // Call moveCharacterLeft in GameManager

            updateCharUI()//when the player moves the character (by clicking the right or left arrow),
            // the game logic will update the character's position or state. After that, updateUI() is called to reflect these changes visually on the screen.
        }

        // Set click listener for rightArrow
        rightArrow.setOnClickListener {
            myGameManager.moveCharacterRight() // will update the position of the character,
             // based on the logic you defined in GameManager

             // SetonClick: When the user clicks the button, the code inside the setOnClickListener block will be executed.
            //lambda function which will be executed when the button is clicked.
            updateCharUI()//calling update UI
        }
    }

    private fun updateUI() {//calling to update all necessary UI elements after any changes happen

        updateCharUI()

        updateHeartsUI()

    }

    private fun updateCharVisibility() {//fun to update characters visibility

        val currentPosition = myGameManager.getCurrentPosition()

        // Reset visibility of all characters
        charVisibility.fill(false) // Set all characters to invisible first

        // Set the current character to visible
        charVisibility[currentPosition] = true

        // Update the visibility of the character views based on the charVisibility logic array
        for (i in charViews.indices) {
            charViews[i].visibility = if (charVisibility[i]) View.VISIBLE else View.INVISIBLE
            //If charVisibility[i] is true, it sets the visibility to View.VISIBLE, else invisible
        }
    }
}

    private fun updateHeartsUI() {
        TODO("Not yet implemented")
    }

    private fun updateCharUI() {
        TODO("Not yet implemented")
    }



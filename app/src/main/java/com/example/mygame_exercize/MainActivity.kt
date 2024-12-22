package com.example.mygame_exercize

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mygame_exercize.utilities.Constants
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

//this class contain the UIlogic
class MainActivity : AppCompatActivity() {

    //defining variables that will be initialized later
    private lateinit var rightArrow: ExtendedFloatingActionButton
    private lateinit var leftArrow: ExtendedFloatingActionButton
    private lateinit var heartsView: Array<AppCompatImageView> //array of hearts,

    // The number of hearts won't change so no need to choose Array collection
    private lateinit var charViews: Array<AppCompatImageView>//array of astronaut char
    private lateinit var astoridViews: Array<Array<AppCompatImageView>>
    private lateinit var myGameManager: SpaceGameManager

    private var gameStarted: Boolean = false//make sure the game loop start only once
    private var activityChangeFlag = false// flag to make sure the activity change will happen only once
    val handler: Handler = Handler(Looper.getMainLooper())//defining handles tasks


    //onCreate is the first method that runs when program starts the MainActivity,
    // it serves as the entry point for setting up my application's UI and initializing components.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Set the layout for this activity
        setContentView(R.layout.activity_main) //Load the activity layout using setContentView

        //Instead of doing everything directly inside onCreate
        //we split tasks to helper functions
        findViews()
        myGameManager = SpaceGameManager(heartsView.size, astoridViews[0].size, astoridViews.size)
        // Initialize charVisibility array of boolean elements based on the number of character views
        initViews()
        startMyGame()
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

        // Initialize the matrix of asteroids so that it has 6 rows and 3 cols
        astoridViews = arrayOf(
            arrayOf(
                findViewById(R.id.main_obstacle1),
                findViewById(R.id.main_obstacle8),
                findViewById(R.id.main_obstacle15)
            ),
            arrayOf(
                findViewById(R.id.main_obstacle2),
                findViewById(R.id.main_obstacle9),
                findViewById(R.id.main_obstacle16)
            ),
            arrayOf(
                findViewById(R.id.main_obstacle3),
                findViewById(R.id.main_obstacle10),
                findViewById(R.id.main_obstacle17)
            ),
            arrayOf(
                findViewById(R.id.main_obstacle4),
                findViewById(R.id.main_obstacle11),
                findViewById(R.id.main_obstacle18)
            ),
            arrayOf(
                findViewById(R.id.main_obstacle5),
                findViewById(R.id.main_obstacle12),
                findViewById(R.id.main_obstacle19)
            ),
            arrayOf(
                findViewById(R.id.main_obstacle6),
                findViewById(R.id.main_obstacle13),
                findViewById(R.id.main_obstacle20)
            )
        )

    }

    private fun initViews() { //set up the interactive behavior for your views
        // after they have been initialized in findViews()

        // Set click listener for leftArrow
        leftArrow.setOnClickListener {
            myGameManager.moveCharacterLeft() // Call moveCharacterLeft in GameManager
            updateCharVisibility()
            //when the player moves the character (by clicking the right or left arrow),
            // the game logic will update the character's position or state. After that, updatevisibilty() is called to reflect these changes visually on the screen.
        }

        // Set click listener for rightArrow
        rightArrow.setOnClickListener {
            myGameManager.moveCharacterRight() // will update the position of the character,
            // based on the logic you defined in GameManager
            updateCharVisibility()
            // SetonClick: When the user clicks the button, the code inside the setOnClickListener block will be executed.
            //lambda function which will be executed when the button is clicked.

        }
    }



    private fun updateCharVisibility() {//fun to update characters visibility(UI)

        val currentPosition = myGameManager.getCurrentPosition()

        // Update the visibility of the character views based on the returned current position
        //first put them all invisible but the current position is visible
        for (characterView in charViews) {
            characterView.visibility = View.INVISIBLE
        }
        charViews[currentPosition].visibility = View.VISIBLE
    }


    val runnable: Runnable = object : Runnable {
        //this is the object contain the game logic and UI updates
        // create inside the game loop
        override fun run() {
            //schedule the Runnable(GAME LOOP) to run again after a certain delay in constants
            handler.postDelayed(this, Constants.GameLogic.DELAY_FOR_RUNNABLE)
            if (myGameManager.checkIfGameIsIsOver()) {//if true meaning game over numberOfFailures= number of lives
                if (!activityChangeFlag) {
                    activityChangeFlag = true //at fist false, changing to true to make sure activity change happen once
                    updateActivity()
                }
            } else {
                myGameManager.updateGameLogic()
                updateMatUI()
                myGameManager.isCollision()
                updateHeartsUI()
            }
        }

        }

        fun updateMatUI() {
            for (i in astoridViews.indices) {
                for (j in astoridViews[i].indices) {
                    if (myGameManager.getElementValue(i, j)) {//if its true
                        astoridViews[i][j].visibility = View.VISIBLE
                    } else {
                        astoridViews[i][j].visibility = View.INVISIBLE
                    }
                }
            }

        }


        fun updateHeartsUI() {//function to update the hearts
            val playerCollisionCounter = myGameManager.getPlayerFailureCounter()
            if(playerCollisionCounter!=0)//meaning if had a collision should remove a heart visiully
            {
              //i will need it to remove the heart on the left
            heartsView[playerCollisionCounter-1].visibility = View.INVISIBLE
            }
        }


    fun startMyGame(){
        //the flag = false is used to prevent multiple calls to startmygame()
       if (!gameStarted){
           handler.postDelayed(runnable,Constants.GameLogic.DELAY_FOR_RUNNABLE)//schedule the runnable
           gameStarted = true; //if gamestarted will turn true, the if will not happen and only 1 instance of runnable
       }
    }

    private fun updateActivity(){
        val intent = Intent(this, EndGameActivity::class.java)
        startActivity(intent)//built-in function in Android used to launch a new activity
        finish()  // Close the current activity (MainActivity)

    }

    }








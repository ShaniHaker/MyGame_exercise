package com.example.mygame_exercize

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mygame_exercize.interfaces.TiltCallBack
import com.example.mygame_exercize.scoreData.Score
import com.example.mygame_exercize.utilities.BackgroundMusicPlayer
import com.example.mygame_exercize.utilities.Constants
import com.example.mygame_exercize.utilities.SharedPreferencesManager
import com.example.mygame_exercize.utilities.SingleSoundPlayer
import com.example.mygame_exercize.utilities.TiltDetector
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textview.MaterialTextView
import android.Manifest
import android.content.pm.PackageManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

//this class contain the UIlogic
class MainActivity : AppCompatActivity() {

    //defining variables that will be initialized later
    private lateinit var rightArrow: ExtendedFloatingActionButton
    private lateinit var leftArrow: ExtendedFloatingActionButton
    private lateinit var heartsView: Array<AppCompatImageView> //array of hearts,

    // The number of hearts won't change so no need to choose Array collection
    private lateinit var charViews: Array<AppCompatImageView>//array of astronaut char
    private lateinit var astoridViews: Array<Array<AppCompatImageView>>
    private lateinit var coinsView: Array<Array<AppCompatImageView>>
    private lateinit var myGameManager: SpaceGameManager
    private lateinit var scoreTextView: MaterialTextView
    private  var selectModeSpeed : Long?= null
    private lateinit var selectModeControl : String
    private lateinit var tiltDetector: TiltDetector

    private lateinit var fusedLocationClient: FusedLocationProviderClient


    private var gameStarted: Boolean = false//make sure the game loop start only once
    private var activityChangeFlag =
        false// flag to make sure the activity change will happen only once
    val handler: Handler = Handler(Looper.getMainLooper())//defining handles tasks


    //onCreate is the first method that runs when program starts the MainActivity,
    // it serves as the entry point for setting up my application's UI and initializing components.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Set the layout for this activity
        setContentView(R.layout.activity_main) //Load the activity layout using setContentView
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //first extract from bundle
        selectModeSpeed = intent.extras?.getLong(Constants.BundleKeys.SPEED_MODE_KEY)!!
        selectModeControl=intent.extras?.getString(Constants.BundleKeys.CONTROL_MODE_KEY)!!



        //Instead of doing everything directly inside onCreate
        //we split tasks to helper functions
        findViews()
        myGameManager = SpaceGameManager(this,heartsView.size, astoridViews[0].size, astoridViews.size)
        // Initialize charVisibility array of boolean elements based on the number of character views
        initTiltDetector()
        initViews()
        //after that the on resume() called automatically
    }

    private fun findViews() {// helper function to locate and assign views to variables= initialize

        //initialize the textView
        scoreTextView = findViewById(R.id.main_Score)

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
            findViewById(R.id.main_astro3_img),
            findViewById(R.id.main_astro4_img),
            findViewById(R.id.main_astro5_img)
        )

        // Initialize the matrix of asteroids so that it has 6 rows and 5 cols
        astoridViews = arrayOf(
            arrayOf(
                findViewById(R.id.main_obstacle1),
                findViewById(R.id.main_obstacle8),
                findViewById(R.id.main_obstacle15),
                findViewById(R.id.main_obstacle21),
                findViewById(
                    (R.id.main_obstacle27)
                )
            ),
            arrayOf(
                findViewById(R.id.main_obstacle2),
                findViewById(R.id.main_obstacle9),
                findViewById(R.id.main_obstacle16),
                findViewById(R.id.main_obstacle22),
                findViewById(R.id.main_obstacle28)
            ),
            arrayOf(
                findViewById(R.id.main_obstacle3),
                findViewById(R.id.main_obstacle10),
                findViewById(R.id.main_obstacle17),
                findViewById(R.id.main_obstacle23),
                findViewById(R.id.main_obstacle29)
            ),
            arrayOf(
                findViewById(R.id.main_obstacle4),
                findViewById(R.id.main_obstacle11),
                findViewById(R.id.main_obstacle18),
                findViewById(R.id.main_obstacle24),
                findViewById(R.id.main_obstacle30)
            ),
            arrayOf(
                findViewById(R.id.main_obstacle5),
                findViewById(R.id.main_obstacle12),
                findViewById(R.id.main_obstacle19),
                findViewById(R.id.main_obstacle25),
                findViewById(R.id.main_obstacle31)
            ),
            arrayOf(
                findViewById(R.id.main_obstacle6),
                findViewById(R.id.main_obstacle13),
                findViewById(R.id.main_obstacle20),
                findViewById(R.id.main_obstacle26),
                findViewById(R.id.main_obstacle32)
            )
        )


        //init the matrix of the coins, connecting views to variable in the code

        coinsView = arrayOf(
            arrayOf(
                findViewById(R.id.main_coin1),
                findViewById(R.id.main_coin8),
                findViewById(R.id.main_coin5),
                findViewById(R.id.main_coin21),
                findViewById(
                    (R.id.main_coin27)
                )
            ),
            arrayOf(
                findViewById(R.id.main_coin2),
                findViewById(R.id.main_coin9),
                findViewById(R.id.main_coin16),
                findViewById(R.id.main_coin22),
                findViewById(R.id.main_coin28)
            ),
            arrayOf(
                findViewById(R.id.main_coin3),
                findViewById(R.id.main_coin10),
                findViewById(R.id.main_coin17),
                findViewById(R.id.main_coin23),
                findViewById(R.id.main_coin29)
            ),
            arrayOf(
                findViewById(R.id.main_coin4),
                findViewById(R.id.main_coin11),
                findViewById(R.id.main_coin18),
                findViewById(R.id.main_coin24),
                findViewById(R.id.main_coin30)
            ),
            arrayOf(
                findViewById(R.id.main_coin5),
                findViewById(R.id.main_coin12),
                findViewById(R.id.main_coin19),
                findViewById(R.id.main_coin25),
                findViewById(R.id.main_coin31)
            ),
            arrayOf(
                findViewById(R.id.main_coin6),
                findViewById(R.id.main_coin13),
                findViewById(R.id.main_coin20),
                findViewById(R.id.main_coin26),
                findViewById(R.id.main_coin32)
            )
        )

    }

    private fun initViews() { //set up the interactive behavior for your views
        // after they have been initialized in findViews() connected between the xml to the code

        //only if the play mode is buttons then need to activate the buttons
        if(selectModeControl == Constants.PlayModes.BUTTONS) {

            Log.d("condition", "initViews:  entred to the if button to move left and right")
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
                // based on the logic defined in GameManager
                updateCharVisibility()
                // SetonClick: When the user clicks the button, the code inside the setOnClickListener block will be executed.
                //lambda function which will be executed when the button is clicked.

            }
        }
        else{
            tiltDetector.stop()
            leftArrow.visibility = View.INVISIBLE
            rightArrow.visibility = View.INVISIBLE
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
            handler.postDelayed(this, selectModeSpeed!!)
            if(myGameManager.checkIfGameIsIsOver()) {//if true meaning game over numberOfFailures= number of lives
                if(!activityChangeFlag) {
                    activityChangeFlag =
                        true //at fist false, changing to true to make sure activity change happen once
                    updateActivity(myGameManager.score)
                }
            } else {
                myGameManager.updateGameLogic()
                updateMatUI()
                myGameManager.isCollision()
                updateHeartsUI()
                updateScoreUI()
            }
        }

    }

    fun updateScoreUI() {
        scoreTextView.text = "${myGameManager.score}"
    }

    fun updateMatUI() {
        for (i in astoridViews.indices) {
            for (j in astoridViews[i].indices) {
                val value = myGameManager.getElementValue(i, j)
                astoridViews[i][j].visibility = if (value == 0) View.VISIBLE else View.INVISIBLE
                coinsView[i][j].visibility = if (value == 1) View.VISIBLE else View.INVISIBLE
            }
        }
    }


    fun updateHeartsUI() {//function to update the hearts
        val playerCollisionCounter = myGameManager.getPlayerFailureCounter()
        if (playerCollisionCounter != 0)//meaning if had a collision should remove a heart visiully
        {
            //i will need it to remove the heart on the left
            heartsView[playerCollisionCounter - 1].visibility = View.INVISIBLE
        }
    }


    //fun startMyGame() {
        //the flag = false is used to prevent multiple calls to startmygame()
        //if (!gameStarted) {
            //handler.postDelayed(
               // runnable,
             //   Constants.GameLogic.DELAY_FOR_RUNNABLE
           // )//schedule the runnable
         //   gameStarted =
       //         true; //if gamestarted will turn true, the if will not happen and only 1 instance of runnable
     //   }
   // }

    private fun updateActivity(score: Int) {
        savePlayerScoresAndLocations("Shani", score)
        val intent = Intent(this, EndGameActivity::class.java)
        var bundle = Bundle()
        bundle.putInt(Constants.BundleKeys.SCORE_KEY,score)
        intent.putExtras(bundle)
        startActivity(intent)//built-in function in Android used to launch a new activity
        finish()  // Close the current activity (MainActivity)

    }

    private fun savePlayerScoresAndLocations(playerName: String, score: Int){
        fetchPlayerCoordinates { playerLocation ->
            val sharedPreferencesManager = SharedPreferencesManager.getInstance()
            sharedPreferencesManager.addScore(
                Score(
                    playerName,
                    score,
                    playerLocation.longitude ,
                    playerLocation.latitude
                )
            )//from where the grade is geografic
            Log.d("entred to score", "score")
        }
    }

    override fun onResume() {
        super.onResume()
        if(!gameStarted && !myGameManager.checkIfGameIsIsOver()){
            if (selectModeControl == Constants.PlayModes.TILT)
                tiltDetector.start() //check if game didn't start yet and not over, start tilt sensors
                handler.postDelayed(runnable,selectModeSpeed!!)//defines the delay according to what user picked
                BackgroundMusicPlayer.getInstance().playMusic()
                gameStarted = true

        }
    }

    override fun onPause() {
        super.onPause()
        if(selectModeControl ==Constants.PlayModes.TILT)
            tiltDetector.stop()//when game stop or no motion tilt stop for resources

            handler.removeCallbacks(runnable)
        gameStarted=false
        BackgroundMusicPlayer.getInstance().pauseMusic()
        //This ensures that when the activity goes into the background (for example, if the user switches to another app),
        // the background music is paused.
    }

   // If you switch to another app (or go back to the home screen), your app moves to the background,
// and onPause() is triggered. When the app comes back to the foreground, onResume() is triggered.


    private fun initTiltDetector() {
        tiltDetector = TiltDetector(
            context = this,
            tiltCallback = object : TiltCallBack {

                override fun tiltLeft() {
                    myGameManager.moveCharacterLeft()
                    updateCharVisibility()
                }

                override fun tiltRight() {
                    myGameManager.moveCharacterRight()
                    updateCharVisibility()
                }

                override fun tiltUp() {
                }

                override fun tiltDown() {
                }

            }
        )
    }

    private fun fetchPlayerCoordinates(callback: (LatLng) -> Unit) {
        // Check if the required location permission is granted
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // If permission is not given, return a default location
            callback(LatLng(0.0, 0.0)) // Default coordinates (latitude: 0.0, longitude: 0.0)
        } else {
            // Attempt to retrieve the last known location
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        // If the location is successfully retrieved, pass it to the callback
                        callback(LatLng(location.latitude, location.longitude))
                    } else {
                        // If the location is null, return a default location
                        callback(LatLng(0.0, 0.0)) // Default coordinates (latitude: 0.0, longitude: 0.0)
                    }
                }
                .addOnFailureListener {
                    // Handle any failure in retrieving the location and return a default location
                    callback(LatLng(0.0, 0.0)) // Default coordinates (latitude: 0.0, longitude: 0.0)
                }
        }
    }


}





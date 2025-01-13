package com.example.mygame_exercize

import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mygame_exercize.fragments.PlayerScoresFragment

class ScoreAndMapActivity : AppCompatActivity() {

    private lateinit var frameofScores : FrameLayout
    private lateinit var frameOfMap :FrameLayout
    private lateinit var playerScoresFragment: PlayerScoresFragment //name of object of type of class PlayerScoresFragment inherit from Fragment class

    //need to create the fragment for map(class,xml) inherits from GoggleMap  and then create instance here



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_score_and_map)

        findViews()
        initViews()


    }

    private fun initViews() {
        //initialize objects
        playerScoresFragment = PlayerScoresFragment() //calling constructor create instance of it
        supportFragmentManager //responsible for adding or removing fragments to the defined xml Frame_scores
            .beginTransaction()
            .add(R.id.FRAME_score,playerScoresFragment)//connecting in the transaction have to!
            .commit()



    }

    private fun findViews() {//connecting from xml to code
        frameofScores = findViewById(R.id.FRAME_score)
        frameOfMap = findViewById(R.id.Frame_map)
        //fragments isn't like view in xml, so we don't connect it, its part of activity

    }
}
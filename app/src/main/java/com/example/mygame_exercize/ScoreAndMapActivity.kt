package com.example.mygame_exercize

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.mygame_exercize.fragments.GoogleMapFragment
import com.example.mygame_exercize.fragments.PlayerScoresFragment
import com.example.mygame_exercize.interfaces.HighScoreItemClicked
import com.example.mygame_exercize.R

class ScoreAndMapActivity : AppCompatActivity(), HighScoreItemClicked {

    private lateinit var playerScoresFragment: PlayerScoresFragment
    private lateinit var googleMapFragment: GoogleMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_and_map)
        Log.d("ScoreAndMapActivity", "onCreate started")

        try {
            initFragments()
            Log.d("ScoreAndMapActivity", "Fragments initialized successfully")
        } catch (e: Exception) {
            Log.e("ScoreAndMapActivity", "Error initializing fragments", e)
            e.printStackTrace()
        }
    }

    private fun initFragments() {
        playerScoresFragment = PlayerScoresFragment()
        googleMapFragment = GoogleMapFragment()

        playerScoresFragment.setItemClickListener(this)

        supportFragmentManager.beginTransaction()
            .replace(R.id.FRAME_score, playerScoresFragment)
            .replace(R.id.Frame_map, googleMapFragment)
            .commit()
    }

    override fun highScoreItemClicked(lat: Double, lon: Double) {
        googleMapFragment.focusOnLocation(lat, lon)
    }
}

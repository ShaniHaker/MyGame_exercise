package com.example.mygame_exercize.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mygame_exercize.R
import com.example.mygame_exercize.scoreData.Score
import com.example.mygame_exercize.utilities.Constants
import com.example.mygame_exercize.utilities.SharedPreferencesManager

class PlayerScoresFragment : Fragment() {
    private lateinit var listOfScores : RecyclerView//recycle View present the Ui of showing list of scores
    private lateinit var scoreAdapter : ScoreAdapter//connect the data to the list
    private val scores = mutableListOf<Score>()


    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_player_scores,container,false)

        listOfScores = view.findViewById(R.id.fragment_list_of_scores)
        listOfScores.layoutManager = LinearLayoutManager(requireContext())

        scoreAdapter = ScoreAdapter(scores)
        listOfScores.adapter = scoreAdapter

        loadScores()
        Log.d("uploaded","u[load")

        return view

    }

    private fun loadScores() {

            val sharedPreferencesManager = SharedPreferencesManager.getInstance()
            val loadedScores = sharedPreferencesManager.getScores(Constants.SPKeys.SCORES_KEY)

            // Clear the existing list and add loaded scores
            scores.clear()
            scores.addAll(loadedScores)

            // Notify adapter about data change
            scoreAdapter.notifyDataSetChanged()
        }
    }



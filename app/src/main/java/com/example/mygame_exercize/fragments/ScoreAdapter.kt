package com.example.mygame_exercize.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mygame_exercize.scoreData.Score
import androidx.recyclerview.widget.RecyclerView
import com.example.mygame_exercize.R
import com.example.mygame_exercize.interfaces.HighScoreItemClicked


class ScoreAdapter(private val scores: List<Score>) :
    RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {

    var highScoreItemClickedCallback: HighScoreItemClicked? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.score_item, parent, false)
        return ScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val score = scores[position]
        holder.nameTextView.text = "Player Name: ${score.name}"
        holder.scoreTextView.text = "Score: ${score.score}"
        holder.lanTextView.text = score.latitude.toString()
        holder.lonTextView.text = score.longitude.toString()



        holder.itemView.setOnClickListener{
            highScoreItemClickedCallback?.highScoreItemClicked(score.latitude, score.longitude)
        }
    }
    override fun getItemCount(): Int = scores.size

    class ScoreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.item_user_name)
        val scoreTextView: TextView = view.findViewById(R.id.item_score_value)
        val lanTextView: TextView = view.findViewById(R.id.item_score_lan)
        val lonTextView: TextView = view.findViewById(R.id.item_score_lon)

    }

}
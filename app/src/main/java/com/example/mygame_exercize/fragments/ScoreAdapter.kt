package com.example.mygame_exercize.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mygame_exercize.scoreData.Score
import androidx.recyclerview.widget.RecyclerView
import com.example.mygame_exercize.R


class ScoreAdapter(private val scores: List<Score>) :
    RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.score_item, parent, false)
        return ScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val score = scores[position]
        holder.nameTextView.text = score.name
        holder.scoreTextView.text = score.score.toString()
    }

    override fun getItemCount(): Int = scores.size

    class ScoreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.item_user_name)
        val scoreTextView: TextView = view.findViewById(R.id.item_score_value)
    }

}
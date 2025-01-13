package com.example.mygame_exercize.scoreData

data class ScoreList private constructor( //class for managing the score lists and only inside this class through builder creating the list
    val name: String,
    val scoresList: List<Score> //list of the scores
) {
    class Builder(
        var name: String = "", //name for score list
        var scoresList: List<Score> = mutableListOf()
    ) {
        fun name(name: String) = apply { this.name = name }
        fun addScore(score: Score) = apply { (this.scoresList as MutableList).add(score) }
        fun build() = ScoreList(name, scoresList)//build the object ScoreList with it's scores
    }
    }

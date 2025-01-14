package com.example.mygame_exercize.scoreData

data class Score(//used for storing data of score

    val name: String,
    val score: Int,
    val longitude: Double,
    val latitude: Double, //for coordinate width
    ) {
        class Builder(//inner class inside the Score that used for creating objects with Builder pattern
            var name: String = "", //default is empty string
            var score: Int = 0,
            var longitude: Double = 0.0,
            var latitude: Double = 0.0,
            var scoreDate: Long = 0L
        ) {
            fun name(name: String) = apply { this.name = name }
            fun score(score: Int) = apply { this.score = score }
            fun longitude(longitude: Double) = apply { this.longitude = longitude }
            fun latitude(latitude: Double) = apply { this.latitude = latitude }
            fun build() = Score(
                name = name,
                score = score,
                longitude = longitude,
                latitude= latitude,
            )
        }
}
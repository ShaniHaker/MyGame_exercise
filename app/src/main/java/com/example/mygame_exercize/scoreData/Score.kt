package com.example.mygame_exercize.scoreData

data class Score(//used for storing data of score

    val name: String,
    val score: Int,
    val longitude: Long,
    val latitude: Long, //for coordinate width
    ) {
        class Builder(//inner class inside the Score that used for creating objects with Builder pattern
            var name: String = "", //default is empty string
            var score: Int = 0,
            var longitude: Long = 0L,
            var latitude: Long = 0L,
            var scoreDate: Long = 0L
        ) {
            fun name(name: String) = apply { this.name = name }
            fun score(score: Int) = apply { this.score = score }
            fun longitude(longitude: Long) = apply { this.longitude = longitude }
            fun latitude(latitude: Long) = apply { this.latitude = latitude }
            fun build() = Score(
                name = name,
                score = score,
                longitude = longitude,
                latitude= latitude,
            )
        }
}
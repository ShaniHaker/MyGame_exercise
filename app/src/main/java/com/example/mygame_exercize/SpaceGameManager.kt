package com.example.mygame_exercize

import androidx.appcompat.widget.AppCompatImageView
import utilities.constants

class SpaceGameManager(private val lives: Int=3,private val numOfCols: Int, private val numOfRows :Int) {
//this class handles the game logic,moving the character, hearts and asteroids

    private var currentCharPosition = constants.intialPosition//char position changes



   fun moveCharacterLeft(){
    if(currentCharPosition>0)//if position is 1 or 2 character can move left
        currentCharPosition--
    }

   fun moveCharacterRight(){
    if(currentCharPosition<=numOfCols-2)//if the character position is in 0 or 1 then can move right otherwise not
        currentCharPosition++
   }

    fun getCurrentPosition(): Int {//return the current position so we can use in main activity for visibility changes
        return currentCharPosition
    }

}
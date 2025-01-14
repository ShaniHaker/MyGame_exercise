package com.example.mygame_exercize

import android.content.Context
import com.example.mygame_exercize.utilities.AlertManager
import com.example.mygame_exercize.utilities.Constants
import com.example.mygame_exercize.utilities.SingleSoundPlayer
import kotlin.random.Random

class SpaceGameManager(//constructor is given 3 columns and 6 rows
    private val context: Context, // Add context to the constructor
    private val numOfLives: Int = 3,
    private val numOfCols: Int,
    private val numOfRows: Int
) {
//this class handles the game logic,moving the character, hearts and asteroids

    private var currentCharPosition =
        Constants.GameLogic.INITIAL_POSTION//char position changes so variable

    //initialize the logic matrix of obstacles with false
    private val logicMatrixOfInts: Array<Array<Int>> =
        Array(numOfRows) { Array(numOfCols) { -1 } } //initialized with -1


    private var playerFailedCounter = 0//count how many time player collide and as a result lives--
    var score = 0//counts the score of the player
       private set


    fun moveCharacterLeft() {
        if (currentCharPosition > 0)//if position is 1 or 2 character can move left
            currentCharPosition--
    }

    fun moveCharacterRight() {
        if (currentCharPosition < numOfCols - 1)//numofcols-1=2
            currentCharPosition++
    }

    fun getCurrentPosition(): Int {//return the current position so we can use in main activity for visibility changes
        return currentCharPosition
    }


    fun randomAstroidOrCoin(): Int {//function that putting randomly number 0 or 2 in one the matrix values in the first row
        return if (Random.nextInt(10) < 7) 0 else 1//change for 0 of 70% and 1 at 30%
        //this refers to the ransom integer that is generated
        //apply return the original object the integer
    }

    fun updateLogicMatrixFirstRow() {
        val columnIndex = Random.nextInt(numOfCols)
        logicMatrixOfInts[0][columnIndex] = randomAstroidOrCoin()
        //fill in the first row the specific chosen element with 1 or 2 based on previos function

    }

    fun isCollision() {
        val valueOfCurrentPosition = logicMatrixOfInts.last()[currentCharPosition]
        if (valueOfCurrentPosition == 0) {
            updateFailureCount()
        }

        else{
            if(valueOfCurrentPosition == 1){
                score++
                playCoinSound() // Play the coin sound when the player collects a coin
            }
        }

            }

    fun playCoinSound() {
        val soundPlayer = SingleSoundPlayer(context)  // Create an instance of SingleSoundPlayer
        soundPlayer.playSound(R.raw.coin_sound)  // Play the coin sound
    }




    //if the current index in lsr row of the logic matrix is 0(asteroid)
        //then need to operate the update failure count =>hit an asteroid

    //last function in kotlin return the last row
    //and  return if the value is true or false in the index of the character


    fun obstacleMovingDownward() {//function to move downwards
        for (i in numOfRows - 1 downTo 1) {
            for (j in 0 until numOfCols) {
                logicMatrixOfInts[i][j] = logicMatrixOfInts[i - 1][j]
            }

        }
        for(i in 0 until numOfCols){//reset the first row values to -1
            logicMatrixOfInts[0][i] = -1
        }

    }

    fun updateGameLogic() {
        obstacleMovingDownward()
        updateLogicMatrixFirstRow()
        //this function control the logic of moving the astroids down and everytime  random a true elemnet in the 0 row
    }

    fun getPlayerFailureCounter(): Int {//return outside the class the number of failure/collison
        return playerFailedCounter
    }

    fun checkIfGameIsIsOver(): Boolean{
       return playerFailedCounter == numOfLives
    }//return true or false, will return true after three collisions if they are equal

//everytime isCollision() return true, the UpdateFailureCount occur(counter++)
    fun updateFailureCount(){

        val soundPlayer = SingleSoundPlayer(context)  // Create an instance of SingleSoundPlayer
        soundPlayer.playSound(R.raw.boom)  // Play the boom sound
        playerFailedCounter++
    toastingAndVibrateAction()//toast
    }

    //this function return Int of the specific value of current element
    fun getElementValue(row: Int, col: Int): Int{
        return logicMatrixOfInts[row][col]//return true/false
    }

    fun toastingAndVibrateAction(){
        AlertManager.getInstance().toast("oh!you hit an asteroid")
        AlertManager.getInstance().vibrate()
    }
}


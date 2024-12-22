package com.example.mygame_exercize

import com.example.mygame_exercize.utilities.AlertManager
import com.example.mygame_exercize.utilities.Constants
import kotlin.random.Random

class SpaceGameManager(//constructor is given 3 columns and 6 rows
    private val numOfLives: Int = 3,
    private val numOfCols: Int,
    private val numOfRows: Int
) {
//this class handles the game logic,moving the character, hearts and asteroids

    private var currentCharPosition = Constants.GameLogic.INITIAL_POSTION//char position changes so variable

    //initialize the logic matrix of obstacles with false
    private val astroidMatrix: Array<Array<Boolean>> =
        Array(numOfRows) { Array(numOfCols) { false } }

    private var playerFailedCounter = 0//count how many time player collide and as a result lives--

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


    fun randomAstroid() {//function that putting randomly a true in one the elements in the first row
        Random.nextInt(numOfCols).apply { astroidMatrix[0][this] = true }
        // generate a number between 0 to 2
        //this refers to the ransom integer that is generated
        //apply return the original object the integer
    }

    fun isCollision() {
        if(astroidMatrix.last()[currentCharPosition]){
           updateFailureCount()
        }
        //last function in kotlin return the last row
        //and  return if the value is true or false in the index of the character
    }

    fun obstacleMovingDownward() {//function to move downwards
        for (i in numOfRows - 1 downTo 1) {
            for (j in 0 until numOfCols) {
                astroidMatrix[i][j] = astroidMatrix[i - 1][j]
            }

        }
        for(i in 0 until numOfCols){//reset the first row values to false
            astroidMatrix[0][i] = false
        }

    }

    fun updateGameLogic() {
        obstacleMovingDownward()
        randomAstroid()
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
        playerFailedCounter++
    toastingAndVibrateAction()
    }

    //this function return boolean-true/false of the specific element
    fun getElementValue(row: Int, col: Int): Boolean{
        return astroidMatrix[row][col]//return true/false
    }

    fun toastingAndVibrateAction(){
        AlertManager.getInstance().toast("oh!you hit an asteroid")
        AlertManager.getInstance().vibrate()
    }
}

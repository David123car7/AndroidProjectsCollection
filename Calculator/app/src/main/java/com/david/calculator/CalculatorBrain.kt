package com.david.calculator

import android.util.Log
import androidx.compose.runtime.Composable

class CalculatorBrain {

    var currentNumber: Double = 0.0
    var operationNumber: Double = 0.0
    var currentOperation: String = ""

    fun InsertNumber(number: String){
        if(currentNumber == 0.0){
            currentNumber = number.toDouble()
        }
        else{
            operationNumber = number.toDouble()
        }
    }

    fun DoOperation(op: String): String{
        if(currentOperation != ""){
            if(currentOperation == "+"){
                currentNumber += operationNumber
                operationNumber = 0.0
                currentOperation = ""
            }
        }
        else{
            currentOperation = op
            return currentOperation
        }

        return currentNumber.toString()
    }
}
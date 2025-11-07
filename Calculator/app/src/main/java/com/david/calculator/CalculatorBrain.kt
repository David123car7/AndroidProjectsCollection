package com.david.calculator

import android.util.Log
import androidx.compose.runtime.Composable

class CalculatorBrain {
    enum class Operation(op: String) {
        ADD("+"),
        SUBTRACT("-"),
        MULTIPLY("×"),
        DIVIDE("÷"),
        EQUAL("="),
        SQRT("√"),
        PERCENTAGE("%"),
        CLEAR("AC"),
        NONE(""),
        CLEAR_ENTRY("C");

        companion object {
            fun parseOperation(op: String): Operation {
                return when (op) {
                    "+" -> ADD
                    "-" -> SUBTRACT
                    "×" -> MULTIPLY
                    "÷" -> DIVIDE
                    "=" -> EQUAL
                    "√" -> SQRT
                    "%" -> PERCENTAGE
                    "AC" -> CLEAR
                    "C" -> CLEAR_ENTRY
                    "" -> NONE
                    else -> EQUAL
                }
            }
        }
    }
    var currentNumber: Double = 0.0
    var operationNumber: Double = 0.0
    var currentOperation: Operation = Operation.NONE

    fun InsertNumber(number: String){
        if(currentNumber == 0.0){
            currentNumber = number.toDouble()
        }
        else{
            operationNumber = number.toDouble()
        }
    }

    fun HandleCalculation(op: String, value: String): String{
        InsertNumber(value)

        var newOperation = Operation.parseOperation(op)
        if(currentOperation != Operation.NONE){
            DoOperation(currentOperation)
        }

        currentOperation = newOperation
        return currentNumber.toString()
    }

    fun DoOperation(operation: Operation){
        if(operation == Operation.ADD){
            currentNumber += operationNumber
            operationNumber = 0.0
        }
    }
}
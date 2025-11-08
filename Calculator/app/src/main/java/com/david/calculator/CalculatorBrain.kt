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
    var currentOperation: Operation = Operation.NONE

    fun HandleCalculation(op: String, value: String): String{
        //InsertNumber(value)

        val newOperation = Operation.parseOperation(op)
        if(currentOperation != Operation.NONE){
            DoOperation(currentOperation, value.toDouble())
        }
        else
            currentNumber = value.toDouble()

        if(newOperation != Operation.EQUAL)
            currentOperation = newOperation
        else
            currentOperation = Operation.NONE

        return currentNumber.toString()
    }

    fun DoOperation(operation: Operation, value: Double){
        if(operation == Operation.ADD){
            currentNumber += value
        }
    }

    fun ResetCalculator(){
        currentNumber = 0.0
        currentOperation = Operation.NONE
    }
}
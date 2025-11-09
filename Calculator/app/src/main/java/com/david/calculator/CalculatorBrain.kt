package com.david.calculator

import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sqrt

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
        val newOperation = Operation.parseOperation(op)

        if(newOperation != Operation.CLEAR_ENTRY){
            if(newOperation == Operation.CLEAR){
                ResetCalculator()
            }
            else if(newOperation == Operation.SQRT){
                currentNumber = sqrt(value.toDouble())
            }
            else if(newOperation == Operation.PERCENTAGE){
                currentNumber /= 100
            }
            else{
                if(currentOperation != Operation.NONE){
                    if(currentOperation == Operation.ADD)
                        currentNumber += value.toDouble()
                    else if(currentOperation == Operation.SUBTRACT)
                        currentNumber -= value.toDouble()
                    else if(currentOperation == Operation.MULTIPLY)
                        currentNumber *= value.toDouble()
                    else if(currentOperation == Operation.DIVIDE)
                        currentNumber /= value.toDouble()                }
                else
                    currentNumber = value.toDouble()

                if(newOperation != Operation.EQUAL)
                    currentOperation = newOperation
                else
                    currentOperation = Operation.NONE
            }
        }

        currentNumber = round(currentNumber * 10.0.pow(6)) / 10.0.pow(6)
        return currentNumber.toString()
    }

    fun ResetCalculator(){
        currentNumber = 0.0
        currentOperation = Operation.NONE
    }
}
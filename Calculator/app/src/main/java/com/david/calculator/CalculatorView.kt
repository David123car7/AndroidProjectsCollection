package com.david.calculator

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp



@Composable
fun CalculatorView(modifier: Modifier = Modifier){

    var displayValue by remember { mutableStateOf("0.0") }
    var displayOperator by remember { mutableStateOf("") }

    val calculatorBrain by remember {  mutableStateOf(CalculatorBrain()) }
    var canDoOperation by remember {  mutableStateOf(false) }
    var endedOperation by remember {  mutableStateOf(false) }

    val onDigitPressed: (String) -> Unit = { digit ->
        //If the operation ended and user presses a digit
        if(endedOperation){
            calculatorBrain.ResetCalculator()
            displayValue = "0.0"
            displayOperator = ""
            endedOperation = false
            canDoOperation = true
        }

        else if(!canDoOperation){
            displayValue = ""
            canDoOperation = true
        }

        if (displayValue == "0.0")
            displayValue = digit;
        else {
            displayValue += digit
        }

        Log.d("Calculator", "Digit pressed: $digit")
    }

    val onOperationPressed: (String) -> Unit = { operation ->
        if(canDoOperation || operation == "AC"){
            if(operation == "=" ){
                displayOperator = ""
                endedOperation = true
                canDoOperation = true
            }
            else{
                if(operation == "√" || operation == "%"){
                    canDoOperation = true
                }
                else{
                    if(operation == "AC"){
                        displayOperator = ""
                    }
                    else{
                        if(operation != "C"){
                            displayOperator = operation
                            endedOperation = false
                        }
                    }
                    canDoOperation = false
                }
            }

            displayValue = calculatorBrain.HandleCalculation(operation, displayValue)
            Log.d("Calculator", "Operation pressed: $operation")
        }
    }

    Box(modifier
        .fillMaxSize()
        .padding(all = 15.dp)){
        Column(modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Row(){
                Text(modifier = Modifier
                    .fillMaxWidth(),
                    text = displayOperator,
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.displaySmall
                )
            }
            Row(){
                Text(modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                    text = displayValue,
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.displayLarge
                )
            }
            Row(){
                CalculatorButton(modifier, onButtonPressed = onOperationPressed, true, "AC")
                CalculatorButton(modifier, onButtonPressed = onOperationPressed, true, "C")
                CalculatorButton(modifier, onButtonPressed = onOperationPressed, true, "√")
                CalculatorButton(modifier, onButtonPressed = onOperationPressed, true, "%")
            }
            Row() {
                CalculatorButton(modifier, onButtonPressed = onDigitPressed, false, "1")
                CalculatorButton(modifier, onButtonPressed = onDigitPressed, false, "2")
                CalculatorButton(modifier, onButtonPressed = onDigitPressed, false, "3")
                CalculatorButton(modifier, onButtonPressed = onOperationPressed, true, "+")
            }
            Row() {
                CalculatorButton(modifier, onButtonPressed = onDigitPressed, false, "4")
                CalculatorButton(modifier, onButtonPressed = onDigitPressed, false, "5")
                CalculatorButton(modifier, onButtonPressed = onDigitPressed, false, "6")
                CalculatorButton(modifier, onButtonPressed = onOperationPressed, true, "-")
            }
            Row() {
                CalculatorButton(modifier, onButtonPressed = onDigitPressed, false, "7")
                CalculatorButton(modifier, onButtonPressed = onDigitPressed, false, "8")
                CalculatorButton(modifier, onButtonPressed = onDigitPressed, false, "9")
                CalculatorButton(modifier, onButtonPressed = onOperationPressed, true, "÷")
            }
            Row() {
                CalculatorButton(modifier, onButtonPressed = onDigitPressed, false, "0")
                CalculatorButton(modifier, onButtonPressed = onDigitPressed, false, ".")
                CalculatorButton(modifier, onButtonPressed = onOperationPressed, true, "=")
                CalculatorButton(modifier, onButtonPressed = onOperationPressed , true, "×")
            }
        }
    }
}

@Composable
@Preview
fun CalculatorPreview(){
    CalculatorView()
}
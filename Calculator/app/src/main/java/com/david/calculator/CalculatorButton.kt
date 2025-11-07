package com.david.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CalculatorButton(modifier: Modifier = Modifier, onButtonPressed: (String) -> Unit, isOperation: Boolean, label: String){
    Button(onClick = {onButtonPressed(label)},
        modifier.size(80.dp).padding(8.dp),
        colors = ButtonDefaults.buttonColors(if(isOperation) Color.Red else Color.White)){
        Text(label,
            style = if ( label.count()==1)
                MaterialTheme.typography.displaySmall else
                MaterialTheme.typography.titleSmall,
            color = Color.Black
        )
    }
}

@Composable
@Preview
fun CalculatorButtonPreview(modifier: Modifier = Modifier){
    CalculatorButton(modifier = Modifier, onButtonPressed = {}, false,"1")
}
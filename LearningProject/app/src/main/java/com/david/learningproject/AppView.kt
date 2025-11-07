package com.david.learningproject

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp

@Composable
fun AppView(name: String, modifier: Modifier = Modifier){

    var buttonText by remember { mutableStateOf("Kazzio") }
    var stateText by remember { mutableStateOf(false) }
    var buttonValueText by remember { mutableStateOf(0) }

    fun changeState(){
        if(stateText){
            buttonText = "Trolado"
            stateText = false;
        }
        else{
            buttonText = "Kazzio"
            stateText = true;
        }
    }
    Box(modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)){
        Row {
            AppButton(onClick = { changeState() }, buttonText, modifier)
            AppButton(onClick = { buttonValueText += 1 }, buttonValueText.toString(), modifier)
        }
    }
    //AppButton(onClick = { buttonValueText += 1 }, buttonText, modifier)
}

@Composable
fun AppButton(onClick: () -> Unit, text: String, modifier: Modifier = Modifier){
    Button(onClick = onClick, modifier){
        Text(text)
    }
}

@Preview()
@Composable()
fun AppViewPreview(modifier: Modifier = Modifier){
    AppView("Welcome to my App!")
}
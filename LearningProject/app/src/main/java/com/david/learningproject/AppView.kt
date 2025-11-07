package com.david.learningproject

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun AppView(name: String, modifier: Modifier = Modifier){

    var buttonText by remember { mutableStateOf("Kazzio") }

    var state = false

    //Greeting(name, modifier)
    AppButton(onClick = {buttonText = "Trolado"}, buttonText, modifier)
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
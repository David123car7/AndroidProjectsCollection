package com.david.shoppinglist.profile

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.david.shoppinglist.firestore.FirestoreDB
import com.david.shoppinglist.navbar.NavbarView
import com.david.shoppinglist.ui.theme.White01


@Composable
fun ProfileView(modifier: Modifier = Modifier, firestoreDB: FirestoreDB, navController: NavController, uid: String){
    val profileViewModel: ProfileViewModel = viewModel()
    val uiState by profileViewModel.uiState

    ProfileViewContent(modifier = modifier, navController = navController, uiState = uiState)

    LaunchedEffect(Unit) {
        Log.d("ProfileView", "UID: ${uid}")
        profileViewModel.fetchProfile(uid = uid, firestoreDB = firestoreDB)
    }
}

@Composable
fun ProfileViewContent(modifier: Modifier, navController: NavController, uiState: ProfileViewModel.ProfileState){
    Column(modifier = modifier.fillMaxSize().background(color = White01)) {
        Box(modifier = Modifier.fillMaxWidth().padding(10.dp), contentAlignment = Alignment.Center){
            Text(modifier = Modifier,
                text = "My Profile",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold)
        }
        Row(modifier = Modifier.fillMaxWidth().padding(10.dp)){
            Text(modifier = Modifier,
                text = uiState.user?.firstName ?: "",
                fontSize = 25.sp)

        }
        Row(modifier = Modifier.fillMaxWidth().padding(10.dp)){
            Text(modifier = Modifier,
                text = uiState.user?.lastName ?: "",
                fontSize = 25.sp)
        }
        Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.BottomEnd){
            NavbarView(modifier = Modifier, navController = navController)
        }
    }
}

@Preview
@Composable
fun ProfilePreview(){
}
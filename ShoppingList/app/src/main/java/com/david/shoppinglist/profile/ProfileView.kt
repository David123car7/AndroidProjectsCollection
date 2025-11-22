package com.david.shoppinglist.profile

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.david.shoppinglist.objects.NavigationViews
import com.david.shoppinglist.firestore.FirestoreDB
import com.david.shoppinglist.models.User
import com.david.shoppinglist.navbar.NavbarView
import com.david.shoppinglist.ui.theme.ShoppingListTheme
import com.david.shoppinglist.ui.theme.White01
import java.time.format.TextStyle


@Composable
fun ProfileView(modifier: Modifier = Modifier, navController: NavController, uid: String?){
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val uiState by profileViewModel.uiState

    ProfileViewContent(modifier = modifier, navController = navController, uiState = uiState,
        onFirstNameUpdate = {value -> profileViewModel.updateFirstName(value)},
        onLastNameUpdate = {value -> profileViewModel.updateLastName(value)},
        onProfileUpdate = { profileViewModel.editProfile(uid = uid)})

    LaunchedEffect(Unit) {
        profileViewModel.fetchProfile(uid = uid)
    }
}

@Composable
fun ProfileViewContent(modifier: Modifier,
                       navController: NavController,
                       uiState: ProfileViewModel.ProfileState,
                       onFirstNameUpdate:(newValue: String)->Unit,
                       onLastNameUpdate:(newValue: String)->Unit,
                       onProfileUpdate:()->Unit){

    Column(modifier = modifier
        .fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), contentAlignment = Alignment.Center){
            Text(modifier = Modifier,
                text = "My Profile",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold)
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), verticalAlignment = Alignment.CenterVertically){
            Text(modifier = Modifier,
                text = "First Name: ",
                fontSize = 25.sp)

            BasicTextField(
                value = uiState.user?.firstName ?: "",
                onValueChange = { newValue -> onFirstNameUpdate(newValue) },
                textStyle = LocalTextStyle.current.copy(fontSize = 25.sp),
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(10.dp)
            )
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), verticalAlignment = Alignment.CenterVertically){
            Text(modifier = Modifier,
                text = "Last Name: ",
                fontSize = 25.sp)

            BasicTextField(
                value = uiState.user?.lastName ?: "",
                onValueChange = { newValue -> onLastNameUpdate(newValue)},
                textStyle = LocalTextStyle.current.copy(fontSize = 25.sp),
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(10.dp)
            )
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), contentAlignment = Alignment.Center){
            Button(modifier = Modifier.padding(8.dp),
                onClick = {
                    onProfileUpdate()
                }){
                Text("Update")
            }
        }
    }
    Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.BottomEnd){
        NavbarView(modifier = Modifier, navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview(){
    ShoppingListTheme() {
        val user = User(firstName = "FirstName", lastName = "lastName")
        val uiState = ProfileViewModel.ProfileState(user = user, error = null)
        ProfileViewContent(modifier = Modifier,
            navController = rememberNavController(),
            uiState = uiState,
            onFirstNameUpdate = { Unit },
            onLastNameUpdate = { Unit },
            onProfileUpdate = { Unit})
    }
}
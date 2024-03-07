package com.example.snapbin.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.snapbin.data.NavigationDrawer
import com.example.snapbin.data.home.HomeViewModel

//import androidx.navigation.NavController

@Composable
fun AccountScreen(navController: NavHostController, homeViewModel: HomeViewModel = viewModel()) {
    NavigationDrawer(navController = navController, homeViewModel = homeViewModel)

}



@Preview
@Composable
fun AccountScreenPreview()
{
    AccountScreen(rememberNavController())
}
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.snapbin.Components.AppToolbar
import com.example.snapbin.Components.NavigationDrawerBody
import com.example.snapbin.Components.NavigationDrawerHeader
import com.example.snapbin.Navigation.Routes
import com.example.snapbin.R
import com.example.snapbin.data.home.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun VolunteerScreen(navController: NavHostController, homeViewModel: HomeViewModel = viewModel()) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    homeViewModel.getUserData()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppToolbar(
                toolbarTitle = stringResource(id = R.string.app_name),
                logoutButtonClicked = {
                    homeViewModel.logout(navController)
                },
                navigationIconClicked = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            NavigationDrawerHeader(homeViewModel.emailId.value)
            NavigationDrawerBody(
                navigationDrawerItems = homeViewModel.navigationItemsList,
                onNavigationItemClicked = { navigationItem ->
                    when (navigationItem.itemId) {
                        "homeScreen" -> navController.navigate(Routes.HOME_SCREEN)
                        "volunteerScreen" -> navController.navigate(Routes.VOLUNTEER_SCREEN)
                        "reportScreen" -> navController.navigate(Routes.REPORT_SCREEN)
                        "accountScreen" -> navController.navigate(Routes.ACCOUNT_SCREEN)
                    }
                    coroutineScope.launch {
                        scaffoldState.drawerState.close()
                    }
                })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Surface(
                color = Color.White,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .padding(bottom = 80.dp), // Adjust this value for the height of the semi-circle
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Add the first clickable button
                    Button(
                        onClick = {
                            navController.navigate(Routes.CHECK_EVENT)
                        },
                        shape = CircleShape, // Rounded from corners
                        modifier = Modifier
                            .padding(16.dp)
                            .size(200.dp) // Adjust the size as needed
                    ) {
                        Text(text = "Check Event")
                    }

                    // Add the second clickable button
                    Button(
                        onClick = {
                            navController.navigate(Routes.CREATE_EVENT)
                        },
                        shape = CircleShape, // Rounded from corners
                        modifier = Modifier
                            .padding(16.dp)
                            .size(200.dp) // Adjust the size as needed
                    ) {
                        Text(text = "Create an Event")
                    }
                }
            }
        }
    }
}

// Custom Shape for semi-circle
@Preview
@Composable
fun VolunteerScreenPreview() {
    // Create a NavController for preview
    val navController = rememberNavController()
    // Create a HomeViewModel for preview
    val homeViewModel = HomeViewModel()

    VolunteerScreen(navController = navController, homeViewModel = homeViewModel)
}


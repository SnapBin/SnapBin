
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.snapbin.Components.AppToolbar
import com.example.snapbin.Components.NavigationDrawerBody
import com.example.snapbin.Components.NavigationDrawerHeader
import com.example.snapbin.Navigation.Routes
import com.example.snapbin.R
import com.example.snapbin.data.home.HomeViewModel
import com.example.snapbin.model.ProfileViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun AccountScreen(navController: NavHostController, homeViewModel: HomeViewModel = viewModel(),
                  profileViewModel: ProfileViewModel = viewModel()) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
//    var selectedImageUri by remember { mutableStateOf<String?>(null) }
//    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
//        // Handle selected image URI here
//        selectedImageUri = uri?.toString()
//    }

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
        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                Box(
//                    modifier = Modifier
//                        .size(120.dp) // Adjust the size of the logo
//                        .clickable {
//                            // Open options for selecting or clicking a photo
//                            launcher.launch("image/*")
//                        },
//                    contentAlignment = Alignment.Center
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .size(120.dp)
//                            .clip(CircleShape) // Clip the Box to a circle shape
//                    ) {
//                        selectedImageUri?.let { uri ->
//                            Image(
//                                painter = rememberAsyncImagePainter(uri),
//                                contentDescription = "Profile Logo",
//                                modifier = Modifier.fillMaxSize()
//                            )
//                        } ?: run {
//                            Image(
//                                painter = painterResource(id = R.drawable.final_logo), // Default image
//                                contentDescription = "Profile Logo",
//                                modifier = Modifier.fillMaxSize()
//                            )
//                        }
//                    }
//                }
//                // Add additional information under the logo
//                // For example:
//                Text(text = "User Name", style = MaterialTheme.typography.subtitle1)
//                Text(text = "User Email", style = MaterialTheme.typography.body2)
//                // Add more information as needed
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ){
                    MiddleBox()
                    Row(
                        modifier= Modifier.padding(horizontal = 68.dp, vertical = 120.dp) ,
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        EditButton(navController)
                    }
                    FetchImage(profileViewModel.userId,navController )
                    InfoBox(profileViewModel.userId)
                }

            }
        }
    }
}
@Composable
fun MiddleBox() {
    Box {
        Card(
            elevation = 15.dp,
            shape= RoundedCornerShape(20.dp),
            backgroundColor= Color.Gray,
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 50.dp)
                .height(180.dp)
                .fillMaxWidth()
        ){
        }
    }
}

@Composable
fun InfoBox(user:String) {

    val db = FirebaseFirestore.getInstance()
    val collectionRef = db.collection("users")
    Log.d(TAG, "Userid is $user")
    val documentRef = collectionRef.document(user)
    var userInfo by remember { mutableStateOf<MutableMap<String, Any>>(mutableMapOf()) }
    var lastName by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        try {
            val documentSnapshot = documentRef.get().await()
            if (documentSnapshot != null) {
                val data = documentSnapshot.data
                Log.d(TAG, "data is : $data")
                if (data != null) {
                    userInfo = data.toMutableMap()
                    lastName = userInfo.getValue("lastname").toString()
                    firstName = userInfo.getValue("firstname").toString()
                    email = userInfo.getValue("email").toString()
                    birthDate = userInfo.getValue("dateofbirth").toString()
                    phoneNumber = userInfo.getValue("phoneNumber").toString()
                }
            }
        } catch (e: Exception) {
            Log.e("******", e.message.toString())
        }
    }
    Info(firstName,lastName,birthDate,phoneNumber,email)
}

@Composable
fun Info(firstName:String , lastName:String , birthDate:String ,phoneNumber : String ,email:String){
    Box(
        modifier = Modifier
            .padding(vertical = 200.dp)
            .fillMaxSize()

    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)

        ) {
            Card(
                backgroundColor = MaterialTheme.colors.primaryVariant,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(0.9f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "personIcon"
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            text = "First Name",
                            color = MaterialTheme.colors.onSurface
                        )
                        Text(
                            text = firstName,
                            color = MaterialTheme.colors.onSurface
                        )
                    }
                }
            }
            Card(
                backgroundColor = MaterialTheme.colors.primaryVariant,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(0.9f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "personIcon"
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            text = "Last Name" ,
                            color = MaterialTheme.colors.onSurface
                        )
                        Text(
                            text = lastName,
                            color = MaterialTheme.colors.onSurface

                        )
                    }
                }
            }
            Card(
                backgroundColor = MaterialTheme.colors.primaryVariant,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(0.9f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "emailIcon"
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            text = "Email",
                            color = MaterialTheme.colors.onSurface
                        )
                        Text(
                            text = email,
                            color = MaterialTheme.colors.onSurface
                        )
                    }
                }
            }
            Card(
                backgroundColor = MaterialTheme.colors.primaryVariant,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(0.9f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "dateIcon"
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            text = "Birth date",
                            color = MaterialTheme.colors.onSurface
                        )
                        Text(
                            text = birthDate,
                            color = MaterialTheme.colors.onSurface
                        )
                    }
                }
            }
            Card(
                backgroundColor = MaterialTheme.colors.primaryVariant,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(0.9f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = "callIcon")
                    Column(
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            text = "Phone number",
                            color = MaterialTheme.colors.onSurface
                        )
                        Text(
                            text = phoneNumber,
                            color = MaterialTheme.colors.onSurface
                        )
                    }
                }
            }
        }

    }
}
@Composable
fun FetchImage(user: String , navController: NavController){
    val db = FirebaseFirestore.getInstance()
    val collectionRef = db.collection("users")
    val documentRef = collectionRef.document(user)
    var userInfo by remember { mutableStateOf<MutableMap<String, Any>>(mutableMapOf()) }
    var urlImage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        try {
            val documentSnapshot = documentRef.get().await()
            if (documentSnapshot != null) {
                val data = documentSnapshot.data
                if (data != null) {
                    userInfo = data.toMutableMap()
                    urlImage = userInfo.getValue("photo").toString()
                }
            }
        } catch (e: Exception) {
            Log.e("******", e.message.toString())
        }
    }
    ProfileImage(navController,urlImage)
}

@Composable
fun ProfileImage(navController: NavController, urlImage : String){
    val showDialog = remember { mutableStateOf(false) }

    Box(
        modifier= Modifier.padding(vertical = 3.dp, horizontal = 150.dp)
    ) {

        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Change Profile Photo",color = MaterialTheme.colors.onSurface)
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "emailIcon",
                            modifier=Modifier.clickable { showDialog.value = false }
                        )
                    } },
                text = { Text(text = "take or upload a photo ",color = MaterialTheme.colors.onSurface) },
                confirmButton = {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
                        Button(
                            onClick = { navController.navigate("camera") }
                        ) {
                            Text(text = "Take A photo",color = MaterialTheme.colors.onSurface)
                        }
                        Button(
                            onClick = { navController.navigate("device") }
                        ) {
                            Text(text = " Upload photo",color = MaterialTheme.colors.onSurface)
                        }
                    }
                }
            )
        }

        Image(
            painter= rememberAsyncImagePainter(model = urlImage),
            contentDescription = "Profile Image",
            modifier = Modifier
                .clip(CircleShape)
                .size(150.dp)
                .clickable { showDialog.value = true }
        )
    }
}

@Composable
fun EditButton(navController: NavController){

    Button(
        onClick = { navController.navigate("Edit")},
        modifier = Modifier.width(width=120.dp)
    ) {
        Row(
        ){
            Text(text="Edit", fontSize = 13.sp,color = MaterialTheme.colors.onSurface)
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Icon(painter = painterResource(id = R.drawable.final_logo ) , contentDescription = "Edit Icon" , modifier= Modifier.size(15.dp))
        }

    }
}

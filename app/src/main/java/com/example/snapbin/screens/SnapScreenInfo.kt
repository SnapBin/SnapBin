package com.example.snapbin.screens


import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.snapbin.Navigation.Routes
import com.example.snapbin.R
import com.example.snapbin.model.SnapScreenViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun SnapScreenInfo(navController: NavController, vm: SnapScreenViewModel = viewModel()) {
    vm.navController = navController
    var selectedButton1 by remember { mutableStateOf(-1) }
    var selectedButton2 by remember { mutableStateOf(-1) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .background(color = Color(0xA7D0A1))
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.size_of_trash),
            fontSize = 20.sp,
            color = colorResource(id = R.color.Bar_Color),
            modifier = Modifier.padding(start = 8.dp, top = 8.dp)
        )
        ThreeRoundButtons(
            selectedButton1 = selectedButton1,
            onButtonClicked1 = { index ->
                // Update the selectedButton state variable when a button is clicked
                selectedButton1 = index
            }
        )
        Spacer(modifier = Modifier.height(16.dp)) // Add spacing between the two button sections
        Text(
            text = stringResource(R.string.type_of_trash),
            fontSize = 20.sp,
            color = colorResource(id = R.color.Bar_Color),
            modifier = Modifier.padding(start = 8.dp, top = 8.dp)
        )
        NineRoundButtons(
            selectedButton = selectedButton2,
            onButtonClicked = { index ->
                // Update the selectedButton state variable when a button is clicked
                selectedButton2 = index
            }
        )
        Spacer(modifier = Modifier.height(20.dp)) // Add spacing between the two button sections
        DropDownBar()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            content = {
//                SaveButton(onClick = { vm.doSnapFunction();navController.navigate(Routes.HOME_SCREEN)})
                SaveSendButton {
                    val sizeOfTrash = when (selectedButton1) {
                        0 -> "Fits in the bag"
                        1 -> "Fits in a wheelbarrow"
                        2 -> "Car Needed"
                        else -> ""
                    }
                    // Determine the selected types of trash
//                    val typeOfTrash = selectedButtons.joinToString(", ") { index ->
//                        if (index < buttonNames.size) buttonNames[index] else ""
//                    }
                    val typeOfTrash = when (selectedButton2) {
                        0 -> "HouseHold"
                        1 -> "Automotive"
                        2 -> "Construction"
                        3 -> "Plastic"
                        4 -> "Electronic"
                        5 -> "Organic"
                        6-> "Metal"
                        7 -> "Liquid"
                        8 -> "Glass"
                        else -> ""
                    }

                    val reportBy = "User Info" // Change this according to your logic
                    Log.d(TAG, "Size of Trash: $sizeOfTrash")
                    Log.d(TAG, "Type of Trash: $typeOfTrash")

                    storeSnapInfo(sizeOfTrash, typeOfTrash, reportBy)
                    navController.navigate(Routes.HOME_SCREEN)
                }
                DraftButton(onClick = { /* Handle Send button click */ })
            }
        )

    }
}

fun storeSnapInfo(sizeOfTrash: String, typeOfTrash: String, reportBy: String) {
    val db = Firebase.firestore
    val userId = Firebase.auth.currentUser?.uid.orEmpty()
    Log.d(TAG, "userID is $userId")
    val query = db.collection("snapInfo")
        .whereEqualTo("userId", userId)
        .orderBy("datetime", Query.Direction.DESCENDING)
        .limit(1)
    println(query)
    Log.d(TAG, "Data is $query")
    // Execute the query asynchronously
    query.get()
        .addOnSuccessListener { querySnapshot ->
            // Check if there are any documents returned
            if (!querySnapshot.isEmpty) {
                // Get the first document from the result
                val documentSnapshot = querySnapshot.documents[0]
                val documentId = documentSnapshot.id
                print(documentId)

                // Get the data from the document
                val data = documentSnapshot.data // Get the HashMap from the DocumentSnapshot
                var location = ""
                var userId = ""
                var description = ""
                var urgency = ""

// Check if data is not null and contains the key
                if (data != null && data.contains("userId")) {
                    userId = data["userId"].toString()
                }
                if (data != null && data.contains("location")) {
                    location = data["location"].toString()
                }
                if (data != null && data.contains("description")) {
                    description = data["description"].toString()
                }
                if (data != null && data.contains("urgency")) {
                    urgency = data["urgency"].toString()
                }
                val snapInfo = hashMapOf(
                    "sizeOfTrash" to sizeOfTrash,
                    "typeOfTrash" to typeOfTrash,
                    "reportBy" to reportBy,
                    "userId" to userId,
                    "location" to location,
                    "datetime" to java.util.Date(),
                    "description" to description,
                    "urgency" to urgency
                )
                // Construct the reference to the document
                val docRef = db.collection("snapInfo").document(documentId)

// Append snapInfo data to the existing document
                docRef.update(snapInfo as Map<String, Any>)
                    .addOnSuccessListener {
                        // Handle success
                        println("snapInfo data appended successfully.")
                    }
                    .addOnFailureListener { e ->
                        // Handle failure
                        println("Error appending snapInfo data: $e")
                    }

                // Print or log the data
                Log.d(TAG, "Data is $data")
            } else {
                Log.d(TAG, "No data found")
            }
        }
        .addOnFailureListener { exception ->
            Log.e(TAG, "Error getting documents: ", exception)
        }

//    // Add a new document with a generated ID
//    db.collection("snapInfo")
//        .add(snapInfo)
//        .addOnSuccessListener { documentReference ->
//            // Log successful addition
//            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//        }
//        .addOnFailureListener { e ->
//            // Log failure
//            Log.w(TAG, "Error adding document", e)
//        }
}

@Composable
fun ThreeRoundButtons(
    selectedButton1: Int,
    onButtonClicked1: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        RoundButton(
            selected = selectedButton1 == 0,
            onClick = { onButtonClicked1(0) },
            modifier = Modifier.padding(8.dp),
            image = painterResource(id = R.drawable.bag),
            buttonText = stringResource(R.string.fits_in_the_bag),
            buttonIndex = 0
        )
        RoundButton(
            selected = selectedButton1 == 1,
            onClick = { onButtonClicked1(1) },
            modifier = Modifier.padding(8.dp),
            image = painterResource(id = R.drawable.wheelborrow),
            buttonText = stringResource(R.string.fits_in_a_wheelbarrow),
            buttonIndex = 1
        )
        RoundButton(
            selected = selectedButton1 == 2,
            onClick = { onButtonClicked1(2) },
            modifier = Modifier.padding(8.dp),
            image = painterResource(id = R.drawable.car),
            buttonText = stringResource(R.string.car_needed),
            buttonIndex = 2
        )
    }
}


@Composable
fun NineRoundButtons(
    selectedButton: Int,
    onButtonClicked: (Int) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            RoundButton(
                selected = selectedButton == 0,
                onClick = { onButtonClicked(0) },
                modifier = Modifier.padding(8.dp),
                image = painterResource(id = R.drawable.household),
                buttonText = stringResource(R.string.household),
                buttonIndex = 0
            )
            RoundButton(
                selected = selectedButton == 1,
                onClick = { onButtonClicked(1) },
                modifier = Modifier.padding(8.dp),
                image = painterResource(id = R.drawable.automotive),
                buttonText = stringResource(R.string.automotive),
                buttonIndex = 1
            )
            RoundButton(
                selected = selectedButton == 2,
                onClick = { onButtonClicked(2) },
                modifier = Modifier.padding(8.dp),
                image = painterResource(id = R.drawable.construction),
                buttonText = stringResource(R.string.construction),
                buttonIndex = 2
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            RoundButton(
                selected = selectedButton == 3,
                onClick = { onButtonClicked(3) },
                modifier = Modifier.padding(8.dp),
                image = painterResource(id = R.drawable.plastic),
                buttonText = stringResource(R.string.plastic),
                buttonIndex = 3
            )
            RoundButton(
                selected = selectedButton == 4,
                onClick = { onButtonClicked(4) },
                modifier = Modifier.padding(8.dp),
                image = painterResource(id = R.drawable.ewaste),
                buttonText = stringResource(R.string.electronic),
                buttonIndex = 4
            )
            RoundButton(
                selected = selectedButton == 5,
                onClick = { onButtonClicked(5) },
                modifier = Modifier.padding(8.dp),
                image = painterResource(id = R.drawable.organic),
                buttonText = stringResource(R.string.organic),
                buttonIndex = 5
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            RoundButton(
                selected = selectedButton == 6,
                onClick = { onButtonClicked(6) },
                modifier = Modifier.padding(8.dp),
                image = painterResource(id = R.drawable.metal),
                buttonText = stringResource(R.string.metal),
                buttonIndex = 6
            )
            RoundButton(
                selected = selectedButton == 7,
                onClick = { onButtonClicked(7) },
                modifier = Modifier.padding(8.dp),
                image = painterResource(id = R.drawable.liquid),
                buttonText = stringResource(R.string.liquid),
                buttonIndex = 7
            )
            RoundButton(
                selected = selectedButton == 8,
                onClick = { onButtonClicked(8) },
                modifier = Modifier.padding(8.dp),
                image = painterResource(id = R.drawable.glasses),
                buttonText = stringResource(R.string.glass),
                buttonIndex = 8
            )
        }
    }
}



@Composable
fun RoundButton(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    image: Painter,
    buttonText: String,
    buttonIndex: Int // Add buttonIndex parameter
) {
    val buttonColor = Color(0xFF52B69A) // Button color always #52B69A
    val borderColor = if (selected) Color.Blue else Color.Transparent // Ring color

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(color = buttonColor, shape = CircleShape)
                .border(2.dp, borderColor, CircleShape)
                .clickable {
                    onClick()
                    // Update selectedButton when the button is clicked
                    // selectedButton = buttonIndex // Uncomment this line if you need to update selectedButton
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                contentScale = ContentScale.Fit
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = buttonText,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}

//@Composable
//fun GridOfButtons(gridName: String, buttonNames: List<String>, buttonImages: List<Int>) {
//    val selectedButtons = remember { mutableStateListOf<Int>() }
//
//    Column(
//        modifier = Modifier.padding(top = 8.dp)
//    ) {
//        // Display grid name
//        Text(
//            text = gridName,
//            fontSize = 16.sp,
//            color = Color.Black,
//            modifier = Modifier.padding(start = 8.dp, top = 8.dp)
//        )
//
//        // Create a grid of buttons
//        for (rowIndex in 0 until 3) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceEvenly
//            ) {
//                for (columnIndex in 0 until 3) {
//                    val index = rowIndex * 3 + columnIndex
//                    val isSelected = selectedButtons.contains(index)
//                    val buttonText = if (index < buttonNames.size) buttonNames[index] else ""
//                    val buttonImage = if (index < buttonImages.size) buttonImages[index] else R.drawable.pointer_green
//
//                    // Create individual button
//                    GridButton(
//                        selected = isSelected,
//                        onClick = {
//                            // Toggle button selection
//                            if (isSelected) {
//                                selectedButtons.remove(index)
//                            } else {
//                                if (selectedButtons.size < 3) {
//                                    selectedButtons.add(index)
//                                }
//                            }
//                        },
//                        buttonText = buttonText,
//                        image = buttonImage,
//                        modifier = Modifier.padding(8.dp)
//                    )
//                }
//            }
//        }
//    }
//}



@Composable
fun GridButton(
    selected: Boolean,
    onClick: () -> Unit,
    buttonText: String,
    image: Int,
    modifier: Modifier = Modifier
) {
    // Button color and border color based on selection state
    val buttonColor = Color(0xFF52B69A)
    val borderColor = if (selected) Color.Blue else Color.Transparent

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable { onClick() } // Toggle button selection
    ) {
        // Button content
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(color = buttonColor, shape = CircleShape)
                .border(2.dp, borderColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                contentScale = ContentScale.Fit
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = buttonText,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}


@Composable
fun DropDownBar() {
    val auth = FirebaseAuth.getInstance()
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }
    var errorText by remember { mutableStateOf("") }

    val options = listOf("User Info", "Anonymous")

    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF52B69A), shape = RoundedCornerShape(4.dp))
                .clickable(onClick = { expanded = true })
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = if (selectedOption.isEmpty()) stringResource(R.string.select_an_option) else selectedOption,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clip(RoundedCornerShape(50))
            )
            if (errorText.isNotEmpty()) {
                Text(
                    text = errorText,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(
                    color = Color(0xFF52B69A),
                    shape = RoundedCornerShape(4.dp)
                ) // Set background color same as dropdown bar
                .padding(start = 16.dp) // Adjust the padding to align with the dropdown bar
        ) {
            options.forEach { item ->
                DropdownMenuItem(onClick = {
                    selectedOption = item
                    expanded = false
                }) {
                    if (item == "User Info" && auth.currentUser != null) {
                        val user = auth.currentUser
                        Text(
                            text = "Logged in as: ${user?.displayName ?: user?.email}",
                            color = Color.White,
                            modifier = Modifier
                                .padding(16.dp)
                                .clip(RoundedCornerShape(50))
                        )
                    } else {
                        Text(
                            text = item,
                            color = Color.White,
                            modifier = Modifier
                                .padding(16.dp)
                                .clip(RoundedCornerShape(50))
                        )
                    }
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(20.dp)) // Add spacing between the two button sections

}

@Composable
fun SaveSendButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(200.dp, 80.dp),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.ToppAppBarColor))

    ) {
        Text(text = stringResource(R.string.save_and_send), color = Color.White)
    }
}

@Composable
fun DraftButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(200.dp, 80.dp),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.ToppAppBarColor))
    ) {
        Text(text = stringResource(R.string.draft), color = Color.White)
    }
}



//@Preview
//@Composable
//fun PreviewSnapScreenInfo() {
//    val navController = rememberNavController()
//    SnapScreenInfo(navController = navController)
//}
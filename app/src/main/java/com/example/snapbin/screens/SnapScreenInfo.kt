package com.example.snapbin.screens


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
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.snapbin.R
import com.example.snapbin.model.SnapScreenViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SnapScreenInfo(navController: NavController, vm: SnapScreenViewModel = viewModel()) {
    vm.navController = navController

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .background(color = Color(0xA7D0A1))
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Size of Trash",
            fontSize = 20.sp,
            color = colorResource(id = R.color.Bar_Color),
            modifier = Modifier.padding(start = 8.dp, top = 8.dp)
        )
        ThreeRoundButtons()
        Spacer(modifier = Modifier.height(16.dp)) // Add spacing between the two button sections
        Text(
            text = "Type of Trash",
            fontSize = 20.sp,
            color = colorResource(id = R.color.Bar_Color),
            modifier = Modifier.padding(start = 8.dp, top = 8.dp)
        )
        GridOfButtons(
            gridName = "",
            buttonNames = listOf("HouseHold", "Automotive", "Construction", "Plastic", "Electronic", "Organic",
                "Metal", "Liquid", "Glass"),
            buttonImages = listOf(
                R.drawable.pointer_blue, R.drawable.pointer_blue, R.drawable.pointer_blue,
                R.drawable.pointer_blue, R.drawable.pointer_blue, R.drawable.pointer_blue,
                R.drawable.pointer_blue, R.drawable.pointer_blue, R.drawable.pointer_blue
            )
        )
        Spacer(modifier = Modifier.height(20.dp)) // Add spacing between the two button sections
        DropDownBar()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            content = {
//                SaveButton(onClick = { vm.doSnapFunction();navController.navigate(Routes.HOME_SCREEN)})
                SaveButton {}
                SendButton(onClick = { /* Handle Send button click */ })
            }
        )

    }
}

@Composable
fun ThreeRoundButtons() {
    // Remember the selected button
    val (selectedButton, setSelectedButton) = remember { mutableStateOf(-1) }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()

    ) {
        RoundButton(
            selectedButton == 0,
            { setSelectedButton(if (selectedButton == 0) -1 else 0) }, // Toggle selection
            Modifier.padding(8.dp),
            painterResource(id = R.drawable.img_1),
            "Fits in the bag"
        )
        RoundButton(
            selectedButton == 1,
            { setSelectedButton(if (selectedButton == 1) -1 else 1) }, // Toggle selection
            Modifier.padding(8.dp),
            painterResource(id = R.drawable.pointer_green),
            "Fits in a wheelbarrow"
        )
        RoundButton(
            selectedButton == 2,
            { setSelectedButton(if (selectedButton == 2) -1 else 2) }, // Toggle selection
            Modifier.padding(8.dp),
            painterResource(id = R.drawable.pointer_blue),
            "Car Needed"
        )
    }
}

@Composable
fun RoundButton(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    image: Painter,
    buttonText: String
) {
    val buttonColor = Color(0xFF52B69A) // Button color always #52B69A
    val borderColor = if (selected) Color.Blue else Color.Transparent // Ring color
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(color = buttonColor, shape = CircleShape)
                .border(2.dp, borderColor, CircleShape)
                .clickable { onClick() },
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

@Composable
fun GridOfButtons(gridName: String, buttonNames: List<String>, buttonImages: List<Int>) {
    val selectedButtons = remember { mutableStateListOf<Int>() }

    val buttonSize = 80.dp
    val gridModifier = Modifier.padding(top = 8.dp)

    Column(
        modifier = gridModifier
    ) {
        Text(
            text = gridName,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.padding(start = 8.dp, top = 8.dp)
        )
        repeat(3) { rowIndex ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(3) { columnIndex ->
                    val index = rowIndex * 3 + columnIndex
                    val isSelected = selectedButtons.contains(index)
                    val buttonText = if (index < buttonNames.size) buttonNames[index] else ""
                    val buttonImage = if (index < buttonImages.size) buttonImages[index] else R.drawable.pointer_green
                    GridButton(
                        selected = isSelected,
                        onClick = {
                            if (isSelected) {
                                selectedButtons.remove(index)
                            } else {
                                if (selectedButtons.size < 3) {
                                    selectedButtons.add(index)
                                }
                            }
                        },
                        buttonText = buttonText,
                        image = buttonImage,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun GridButton(selected: Boolean, onClick: () -> Unit, buttonText: String, image: Int,
               modifier: Modifier = Modifier
) {
    val buttonColor = Color(0xFF52B69A)
    val borderColor = if (selected) Color.Blue else Color.Transparent

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(color = buttonColor, shape = CircleShape)
                .border(2.dp, borderColor, CircleShape),
            contentAlignment = Alignment.Center // Center the content in the Box
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
                text = if (selectedOption.isEmpty()) "Select an option" else selectedOption,
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
fun SaveButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(200.dp, 80.dp),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF52B69A))
    ) {
        Text(text = "Save", color = Color.White)
    }
}

@Composable
fun SendButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(200.dp, 80.dp),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF52B69A))
    ) {
        Text(text = "Send", color = Color.White)
    }
}



//@Preview
//@Composable
//fun PreviewSnapScreenInfo() {
//    val navController = rememberNavController()
//    SnapScreenInfo(navController = navController)
//}
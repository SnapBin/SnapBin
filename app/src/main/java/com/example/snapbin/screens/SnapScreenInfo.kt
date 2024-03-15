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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.snapbin.R

@Composable
fun SnapScreenInfo(navController: NavController) {
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
            buttonNames = listOf("HouseHold", "Button 2", "Button 3", "Button 4", "Button 5", "Button 6", "Button 7", "Button 8", "Button 9"),
            buttonImages = listOf(
                R.drawable.pointer_blue, R.drawable.pointer_blue, R.drawable.pointer_blue,
                R.drawable.pointer_blue, R.drawable.pointer_blue, R.drawable.pointer_blue,
                R.drawable.pointer_blue, R.drawable.pointer_blue, R.drawable.pointer_blue
            )
        )
        Spacer(modifier = Modifier.height(16.dp)) // Add spacing between the two button sections

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
            painterResource(id = R.drawable.pointer_green),
            "Button 1"
        )
        RoundButton(
            selectedButton == 1,
            { setSelectedButton(if (selectedButton == 1) -1 else 1) }, // Toggle selection
            Modifier.padding(8.dp),
            painterResource(id = R.drawable.pointer_green),
            "Button 2"
        )
        RoundButton(
            selectedButton == 2,
            { setSelectedButton(if (selectedButton == 2) -1 else 2) }, // Toggle selection
            Modifier.padding(8.dp),
            painterResource(id = R.drawable.pointer_blue),
            "Button 3"
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
fun GridButton(
    selected: Boolean,
    onClick: () -> Unit,
    buttonText: String,
    image: Int,
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
@Preview
@Composable
fun PreviewSnapScreenInfo() {
    val navController = rememberNavController()
    SnapScreenInfo(navController = navController)
}
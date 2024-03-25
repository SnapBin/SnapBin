package com.example.snapbin.Components


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snapbin.R
import com.example.snapbin.data.NavigationItem
import com.example.snapbin.ui.theme.Bar_Color
import com.example.snapbin.ui.theme.GrayColor
import com.example.snapbin.ui.theme.TextColor
import com.example.snapbin.ui.theme.TopGreen
import com.example.snapbin.ui.theme.nav_bar
import com.example.snapbin.ui.theme.whiteColor

@Composable
fun NormalTextComponents(value: String) {

    TopAppBar (
        modifier = Modifier.height(70.dp),
        backgroundColor = MaterialTheme.colors.primaryVariant,
        title = {
            Text(
                text="SnapBin",
                fontSize = 32.sp ,
                fontWeight= FontWeight.Medium,
                color= Color.Black,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

        },
//        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Yellow)
    )

}
@Composable
fun WelcomeComponent(value: String) {
    Text(
        text = value,
        fontSize = 24.sp,
        fontWeight = FontWeight.Medium,
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun MyTextFieldComponent(labelValue: String, imageVector: ImageVector,
                         onTextSelected: (String) -> Unit,
                         errorStatus: Boolean = false

){
    val textValue = remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),

        label = {Text(text = labelValue)},

        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Bar_Color,
            focusedLabelColor = Bar_Color,
            cursorColor = Bar_Color,
            backgroundColor = Bar_Color
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon( imageVector = imageVector, contentDescription = ""
            )
        },
        isError = !errorStatus

    )

}
@Composable
fun EquipementComponent(labelValue: String,
                         onTextSelected: (String) -> Unit

){
    val textValue = remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),

        label = {Text(text = labelValue)},

        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Bar_Color,
            focusedLabelColor = Bar_Color,
            cursorColor = Bar_Color,
            backgroundColor = Bar_Color,
            textColor = Color.Black
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        }

    )

}

@Composable
fun ContactComponent(labelValue: String,
                        onTextSelected: (String) -> Unit

){
    val textValue = remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),

        label = {Text(text = labelValue)},

        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Bar_Color,
            focusedLabelColor = Bar_Color,
            cursorColor = Bar_Color,
            backgroundColor = Bar_Color,
            textColor = Color.Black
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },


    )

}

@Composable
fun LocationComponent(labelValue: String,
                     onTextSelected: (String) -> Unit
){
    val textValue = remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),

        label = {Text(text = labelValue)},

        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Bar_Color,
            focusedLabelColor = Bar_Color,
            cursorColor = Bar_Color,
            backgroundColor = Bar_Color,
            textColor = Color.Black
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },


    )

}

@Composable
fun PasswordFieldComponent(labelValue : String, imageVector: ImageVector,
                           onTextSelected: (String) -> Unit,
                           errorStatus: Boolean = false){
    val locakFocusManager = LocalFocusManager.current
    val password = remember { mutableStateOf("") }

    val passwordVisible = remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),

        label = {Text(text = labelValue)},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Bar_Color,
            focusedLabelColor = Bar_Color,
            cursorColor = Bar_Color,
            backgroundColor = Bar_Color
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        singleLine = true,
        keyboardActions = KeyboardActions{locakFocusManager.clearFocus()
        },
        maxLines = 1,
        value = password.value,
        onValueChange = {
            password.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon( imageVector = imageVector, contentDescription = ""
            )
        },
        trailingIcon = {
            val iconImage = if (passwordVisible.value) {
                Icons.Filled.Visibility
            }
            else {
                Icons.Filled.VisibilityOff
            }

            var description = if (passwordVisible.value) {
                stringResource(R.string.Hide_password)
            }
            else {
                stringResource(R.string.Show_Password)
            }
            IconButton(onClick = { passwordVisible.value = !passwordVisible.value}) {
                Icon(imageVector = iconImage, contentDescription = description)

            }

        },

        visualTransformation = if (passwordVisible.value) {
            VisualTransformation.None
        }
        else {
            PasswordVisualTransformation()
        },
        isError = !errorStatus
    )

}

@Composable
fun ConfirmPasswordFieldComponent(
    labelValue: String,
    imageVector: ImageVector,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false
) {
    val passwordVisible = remember { mutableStateOf(false) }
    val localFocusManager = LocalFocusManager.current
    val confirmPassword = remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Bar_Color,
            focusedLabelColor = Bar_Color,
            cursorColor = Bar_Color,
            backgroundColor = Bar_Color
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        keyboardActions = KeyboardActions {
            localFocusManager.clearFocus()
        },
        maxLines = 1,
        value = confirmPassword.value,
        onValueChange = {
            confirmPassword.value = it
            onTextSelected(it)
        }, leadingIcon = {
            Icon( imageVector = imageVector, contentDescription = ""
            )
        },
        trailingIcon = {
            val iconImage = if (passwordVisible.value) {
                Icons.Filled.Visibility
            }
            else {
                Icons.Filled.VisibilityOff
            }

            var description = if (passwordVisible.value) {
                stringResource(R.string.Hide_password)
            }
            else {
                stringResource(R.string.Show_Password)
            }
            IconButton(onClick = { passwordVisible.value = !passwordVisible.value}) {
                Icon(imageVector = iconImage, contentDescription = description)

            }

        },

        visualTransformation = if (passwordVisible.value) {
            VisualTransformation.None
        }
        else {
            PasswordVisualTransformation()
        },
        isError = !errorStatus
    )
}


@Composable
fun CheckboxComponents (value: String, onTextSelected: (String) -> Unit, onCheckedChange: (Boolean) -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .heightIn(50.dp)
        .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ){
        val checkState = remember {
            mutableStateOf(false)
        }
        Checkbox(checked = checkState.value,
            onCheckedChange = {
                checkState.value != checkState.value
                onCheckedChange.invoke(it)
            })
        ClicableTextComponents(value = value, onTextSelected)

    }
}

@Composable
fun ClicableTextComponents(value: String, onTextSelected: (String) -> Unit){
    val initialText = "By continuing you accept our "
    val privacyPolicyText = "Privacy Policy "
    val andtext = " and "
    val termsandConditionstext = " Term of Use"
    val annotedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = Bar_Color)){
            pushStringAnnotation(tag = privacyPolicyText, annotation = privacyPolicyText)
            append(privacyPolicyText)
        }
        append(andtext)
        withStyle(style = SpanStyle(color = Bar_Color)){
            pushStringAnnotation(tag = termsandConditionstext, annotation = termsandConditionstext)
            append(termsandConditionstext)
        }

    }

    ClickableText(text = annotedString, onClick = {offset ->

        annotedString.getStringAnnotations(offset,offset)
            .firstOrNull()?.also {span->
                Log.d("ClicableTextComponents", "{$span}")

                if((span.item == termsandConditionstext) || (span.item == privacyPolicyText)){
                    onTextSelected(span.item)

                }
            }
    })

}

@Composable
fun ButtonComponent(value: String, onButtonClicked : ()-> Unit, isEnabled : Boolean = false){
    Button(
        onClick = {
            onButtonClicked.invoke()
        },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(0.dp)
            .padding(15.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        enabled = isEnabled

    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .heightIn(50.dp)
            .background(
                brush = Brush.horizontalGradient(listOf(TopGreen, Bar_Color)),
                shape = RoundedCornerShape(10.dp)
            ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold)
        }

    }
}

@Composable
fun DividerTextComponent() {
    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .weight(1f),

            color = GrayColor,
            thickness = 1.dp)

        Text(modifier = Modifier.padding(8.dp), text = "or", fontSize = 18.sp, color = TextColor)
        Divider(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            color = GrayColor,
            thickness = 1.dp)
    }
}

@Composable
fun ClicableLoginTextComponents(tryingToLogin: Boolean = true, onTextSelected: (String) -> Unit){
    val initialText = if(tryingToLogin) "Already have an account? " else "Don't have an account yet? "
    val loginText = if(tryingToLogin)" Login " else " Register "

    val annotedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = Bar_Color)){
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(loginText)
        }

    }

    ClickableText(modifier = Modifier
        .fillMaxWidth()
        .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center
        ),
        text = annotedString, onClick = {offset ->

            annotedString.getStringAnnotations(offset,offset)
                .firstOrNull()?.also {span->
                    Log.d("ClicableTextComponents", "{$span}")

                    if((span.item == loginText)){
                        onTextSelected(span.item)

                    }
                }
        })

}

@Composable
fun NormalTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ), color = colorResource(id = R.color.Bar_Color),
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline
    )
}

@Composable
fun AppToolbar(toolbarTitle: String, logoutButtonClicked: () -> Unit, navigationIconClicked: () -> Unit){

    TopAppBar (
        modifier = Modifier.height(70.dp),
        backgroundColor = MaterialTheme.colors.primaryVariant,
        title = {
            Text(
                text="SnapBin",
                fontSize = 32.sp ,
                fontWeight= FontWeight.Medium,
                color= Color.Black,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

        },
//        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Yellow)
        navigationIcon = {
            IconButton(onClick = {
                navigationIconClicked.invoke() }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = stringResource(R.string.menu),
                    tint = whiteColor
                )

            }

        },
        actions = {
            IconButton(onClick = {
                logoutButtonClicked()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = stringResource(id = R.string.logout)
                )
            }
        }

    )
}
@Composable
fun NavigationDrawerHeader(value: String?) {
    Box(
        modifier = Modifier
            .background(
                Brush.horizontalGradient(
                    listOf(nav_bar, nav_bar)
                )
            )
            .fillMaxWidth()
            .height(180.dp)
            .padding(32.dp)
    ) {

        NavigationDrawerText(
            title = value?:stringResource(R.string.navigation_header), 28.sp , nav_bar
        )

    }
}

@Composable
fun NavigationDrawerBody(navigationDrawerItems: List<NavigationItem>,
                         onNavigationItemClicked:(NavigationItem) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {

        items(navigationDrawerItems) {
            NavigationItemRow(item = it,onNavigationItemClicked)
        }

    }
}

@Composable
fun NavigationItemRow(item: NavigationItem,
                      onNavigationItemClicked:(NavigationItem) -> Unit)
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onNavigationItemClicked.invoke(item)
            }
            .padding(all = 20.dp)
    ) {

        Icon(
            imageVector = item.icon,
            contentDescription = item.description,
        )

        Spacer(modifier = Modifier.width(25.dp))

        NavigationDrawerText(title = item.title, 20.sp, nav_bar)


    }
}

@Composable
fun NavigationDrawerText(title: String, textUnit: TextUnit, color: Color) {

    val shadowOffset = Offset(1f, 3f)

    Text(
        text = title, style = TextStyle(
            color = Color.White,
            fontSize = textUnit,
            fontStyle = FontStyle.Normal,
            shadow = Shadow(
                color = nav_bar,
                offset = shadowOffset, 2f
            )
        )
    )
}


@Preview
@Composable
fun Default_preview() {
    NormalTextComponents(value = "SnapBin")
    WelcomeComponent(value = "Welcome")
}
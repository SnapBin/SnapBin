package com.example.snapbin.Components


import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snapbin.R
import com.example.snapbin.ui.theme.Bar_Color

@Composable
fun NormalTextComponents(value: String) {
    TopAppBar (
        title = { Text(
            text="SnapBin" ,

            fontSize = 32.sp ,
            fontWeight= FontWeight.Medium,
            color= Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            textAlign = TextAlign.Center
            )

        }
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
fun MyTextFieldComponent(labelValue: String, imageVector: ImageVector){
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
        keyboardOptions = KeyboardOptions.Default,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
        },
        leadingIcon = {
            Icon( imageVector = imageVector, contentDescription = ""
            )
        }
    )

}

@Composable
fun PasswordFieldComponent(labelValue : String, imageVector: ImageVector ){
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
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        value = password.value,
        onValueChange = {
            password.value = it
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
        }
    )

}

@Composable
fun CheckboxComponents (value: String, onTextSelected: (String) -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .heightIn(56.dp),
        verticalAlignment = Alignment.CenterVertically,
    ){
      val checkState = remember {
          mutableStateOf(false)
      }
        Checkbox(checked = checkState.value,
            onCheckedChange = {
                checkState.value != checkState.value
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

@Preview
@Composable
fun Default_preview() {
    NormalTextComponents(value = "SnapBin")
    WelcomeComponent(value = "Welcome")
}
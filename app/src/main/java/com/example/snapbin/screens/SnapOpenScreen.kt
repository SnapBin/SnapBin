package com.example.snapbin.screens

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.snapbin.Navigation.Routes
import com.example.snapbin.data.SnapImageDownloader
import com.example.snapbin.model.MainNavViewModel
import com.example.snapbin.model.SnapScreenViewModel
import com.example.snapbin.model.data.Snap
import com.example.snapbin.model.data.Urgency
import com.example.snapbin.view.ErrorCard
import java.net.URLDecoder


@Composable
fun OpenSnapScreen(snap: Snap, navController: NavController, mainNavViewModel: MainNavViewModel,
                   isNew: Boolean, vm: SnapScreenViewModel = viewModel()) {
    val primaryColorTrasparent = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
    val secondaryColorTrasparent = MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f)
    var showDeleteDialog : MutableState<Boolean> = remember { mutableStateOf(false) }
    var showShareDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    vm.isNew = isNew
    vm.snapId = snap.id
    if(!isNew) vm.description.value = snap.description
    vm.location.value = snap.location
    if(!isNew) vm.urgency.value = snap.urgency
    vm.navController = navController
    mainNavViewModel.currentFloatingActionButton.value = {
        FloatingActionButton(
            containerColor =  MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            onClick = {
                if(!vm.inProgress.value) {
                    if (!isNew) showDeleteDialog.value = true
                    else {
                        showDeleteDialog.value = false
                        vm.doSnapFunction()
                    }
                }
            }
        ) {
            if(vm.inProgress.value) CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimaryContainer
            ) else {
                Icon(
                    if (!isNew) Icons.Filled.Delete else Icons.Filled.Add,
                    contentDescription = null,
                )
                if (showDeleteDialog.value && !isNew) {
                    DeleteDialog(onDismiss = { showDeleteDialog.value = false }, {
                        vm.doSnapFunction()
                    })
                }
            }
        }
    }
    val configuration = LocalConfiguration.current
    LaunchedEffect(snap) {
        if (isNew)
            vm.snapUrl.value = Uri.parse(snap.snapImageUrl)
        else
            vm.snapUrl.value = SnapImageDownloader.downloadSnap(context,snap)
    }
    Column {
        Box(
            Modifier
                .fillMaxWidth()
                .requiredHeight((configuration.screenHeightDp * 0.25).dp)

        ) {

            if(vm.snapUrl.value == Uri.EMPTY)
                Row(modifier=Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center){
                    Spacer(modifier = Modifier.height(5.dp))
                    CircularProgressIndicator()
                }
            else
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop,
                    painter = rememberAsyncImagePainter(URLDecoder.decode(vm.snapUrl.value.toString(),"UTF-8"),onError = { Log.e("SNAPBIN_IMAGE",it.result.throwable.message ?: "")}),
                    contentDescription = "My Image"
                )
        }
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxHeight()
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            if(vm.error.value != null){
                ErrorCard(error = stringResource(id = vm.error.value!!))
                Spacer(modifier = Modifier.height(10.dp))
            }
            Row(){
                Column(){
                    Text(text = "Location", fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "${"%.${4}f".format(snap.location.latitude)}, ${"%.${4}f".format(snap.location.longitude)}",
                        color = MaterialTheme.colorScheme.inverseSurface,
                        fontSize = 16.sp,
                    )
                }
                Spacer(modifier = Modifier.width(40.dp))
                Column(){
                    Text(text = "Date", fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = snap.getFormattedDate(),
                        color = MaterialTheme.colorScheme.inverseSurface,
                        fontSize = 16.sp)

                }

            }

            /* previous code just Location
            Text(text = stringResource(com.snaptrash.snaptrash.R.string.word_location), fontSize = 18.sp)
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "${"%.${4}f".format(snap.location.latitude)}, ${"%.${4}f".format(snap.location.longitude)}",
                fontSize = 16.sp,
            )

             */
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Description", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(5.dp))
            Box(
                Modifier
                    .height((configuration.screenHeightDp * 0.25).dp)
                    .fillMaxWidth()
                    //.aspectRatio(1.0f) // fixed aspect ratio
                    //.clip(RoundedCornerShape(8.dp))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        //shape = RoundedCornerShape(8.dp)
                    )
                    .background(color = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                OutlinedTextField(
                    value = vm.description.value,
                    enabled = isNew,
                    modifier = Modifier.fillMaxSize(),
                    onValueChange ={vm.description.value = it})
            }
            Spacer(Modifier.height(20.0.dp))
            Text("Urgency",fontSize = 18.sp)
            Slider(
                value = vm.urgency.value.value.toFloat(),
                enabled = isNew ,
                steps = Urgency.values().size,
                valueRange = 0.0f..Urgency.values().size.toFloat() - 1.0f,
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.onBackground,
                    activeTickColor = MaterialTheme.colorScheme.onBackground,
                    activeTrackColor = MaterialTheme.colorScheme.onBackground,
                    disabledActiveTickColor = MaterialTheme.colorScheme.onBackground,
                    disabledActiveTrackColor = MaterialTheme.colorScheme.onBackground),
                onValueChange = {vm.urgency.value = Urgency.values()[it.toInt()]}
            )
            Text(
                text = when(vm.urgency.value){
                    Urgency.NOT_URGENT -> stringResource(com.example.snapbin.R.string.not_urgent)
                    Urgency.SERIOUS -> stringResource(com.example.snapbin.R.string.word_serious)
                    Urgency.URGENT -> stringResource(com.example.snapbin.R.string.word_urgent)
                },
                modifier = Modifier.fillMaxWidth(),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(25.dp))
//            Button(onClick = { vm.doSnapFunction();navController.navigate(Routes.HOME_SCREEN)}) {
//                Text(text = "Map Screen")
//            }

            Button(onClick = { navController.navigate(Routes.SnapScreenInfo)}) {
                Text(text = "Trash Info")
            }
        }
    }
}



@Composable
fun DeleteDialog(onDismiss: () -> Unit,onApproval: ()->Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(com.example.snapbin.R.string.label_delete_item)) },
        text = { Text(text = stringResource(com.example.snapbin.R.string.question_are_you_sure_to_delete)) },
        confirmButton = {
            Button(
                onClick = {onApproval() },
                content = { Text(text = stringResource(com.example.snapbin.R.string.word_delete)) }
            )
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                content = { Text(text = stringResource(com.example.snapbin.R.string.label_go_back)) }
            )
        }
    )
}




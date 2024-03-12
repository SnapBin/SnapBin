package com.example.snapbin.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.snapbin.Navigation.Routes
import com.example.snapbin.model.data.Snap
import com.example.snapbin.model.data.SnapStatus
import com.example.snapbin.view.SnapCard


@Composable
fun ListSnapScreen(snapList: List<Snap>, navController: NavController, isHistory: Boolean){
    val state = rememberLazyListState()
    LazyColumn(modifier = Modifier.fillMaxSize()){
        snapList.filter {(isHistory && it.status != SnapStatus.PENDING) || (!isHistory && it.status == SnapStatus.PENDING) }.
        forEach {
            item {
                SnapCard(snap = it,onClick = {
                    navController.navigate(Routes.SINGLE_SNAP.replace("{snap}",it.encodeForNavigation()))
                })
            }
        }
    }
}
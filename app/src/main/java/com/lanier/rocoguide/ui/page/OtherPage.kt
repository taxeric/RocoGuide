package com.lanier.rocoguide.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.lanier.rocoguide.ui.common.SingleTitle

/**
 * Create by Eric
 * on 2022/7/25
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtherScreen(navHostController: NavHostController, title: String){
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            SmallTopAppBar(
                title = { Text(text = title) },
            )
        }
    ){ innerPadding ->
        OthersMain(navHostController, padding = innerPadding)
    }
}

@Composable
fun OthersMain(navHostController: NavHostController, padding: PaddingValues){
    Column(modifier = Modifier.padding(padding)) {
        OtherCS(navHostController)
        Spacer(modifier = Modifier.height(10.dp))
        OtherDT(navHostController)
    }
}

@Composable
fun OtherCS(navHostController: NavHostController){
    Column(modifier = Modifier.fillMaxWidth()) {
        SingleTitle(title = "CS")
        OtherHorizontalTextItem("技能大全")
    }
}

@Composable
fun OtherDT(navHostController: NavHostController){
    Column(modifier = Modifier.fillMaxWidth()) {
        SingleTitle(title = "DT")
        OtherHorizontalTextItem("版本")
        OtherHorizontalTextItem("关于")
    }
}

@Composable
private fun OtherHorizontalTextItem(title: String, click: () -> Unit = {}){
    Text(text = title, modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.background)
        .clickable {
            click()
        }
        .padding(10.dp))
}

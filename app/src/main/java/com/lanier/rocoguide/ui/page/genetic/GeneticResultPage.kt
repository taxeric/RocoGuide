package com.lanier.rocoguide.ui.page.genetic

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.lanier.rocoguide.ui.common.EnableBackBaseScaffoldWithActions
import com.lanier.rocoguide.ui.common.GeneticDialog

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/29 10:22
 * Desc  :
 */
@Composable
fun GeneticResultScreen(navHostController: NavHostController, title: String){
    var showGeneticDialog by remember {
        mutableStateOf(false)
    }
    EnableBackBaseScaffoldWithActions(
        title = title,
        onBack = { navHostController.popBackStack() },
        actions = {
            IconButton(onClick = {
                showGeneticDialog = true
            }) {
                Icon(imageVector = Icons.Filled.Warning, contentDescription = "")
            }
        },
    ) {
        GeneticResultImpl(navHostController, paddingValues = it)
    }
    if (showGeneticDialog) {
        GeneticDialog {
            if (it == 0) {
            }
            showGeneticDialog = false
        }
    }
}

@Composable
fun GeneticResultImpl(navHostController: NavHostController, paddingValues: PaddingValues){
    Column(modifier = Modifier.padding(paddingValues)) {
    }
}

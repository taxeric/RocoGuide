package com.lanier.rocoguide.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.lanier.rocoguide.ui.common.EnableBackBaseScaffold
import com.lanier.rocoguide.ui.theme.ExtendedTheme

/**
 * Create by Eric
 * on 2022/9/12
 */
@Composable
fun BGMScreen(navHostController: NavHostController, title: String){
    EnableBackBaseScaffold(title, onBack = {
        navHostController.popBackStack()
    }) {
        BGMScreenImpl(it)
    }
}

@Composable
fun BGMScreenImpl(paddingValues: PaddingValues){
    Column(modifier = Modifier
        .padding(paddingValues)
        .background(ExtendedTheme.colors.defaultMainBackground)) {
    }
}

@Composable
fun BGMScreenList(){
}

@Composable
fun BGMScreenItem(){
}
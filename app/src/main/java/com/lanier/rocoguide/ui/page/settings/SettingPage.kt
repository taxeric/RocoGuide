package com.lanier.rocoguide.ui.page.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.lanier.rocoguide.ui.common.CommonBaseScaffold
import com.lanier.rocoguide.ui.common.TitleBarSize

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/9/8 17:23
 * Desc  :
 */
@Composable
fun SettingsScreen(navHostController: NavHostController, title: String){
    CommonBaseScaffold(title = title, onBack = { navHostController.popBackStack() }, titleBarSize = TitleBarSize.Medium) {
        SettingsScreenImpl(paddingValues = it)
    }
}

@Composable
fun SettingsScreenImpl(paddingValues: PaddingValues){
    Column(modifier = Modifier.padding(paddingValues)) {
    }
}

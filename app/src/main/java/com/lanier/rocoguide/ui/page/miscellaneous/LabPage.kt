package com.lanier.rocoguide.ui.page.miscellaneous

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.lanier.rocoguide.R
import com.lanier.rocoguide.ui.common.CommonBaseScaffold
import com.lanier.rocoguide.ui.common.TitleBarSize
import com.lanier.rocoguide.ui.common.TitleTextWithRipple

/**
 * Create by Eric
 * on 2023/1/24
 */
@Composable
fun LabScreen(navHostController: NavHostController, title: String) {
    CommonBaseScaffold(
        title = title,
        showNavigationIcon = true,
        onBack = { navHostController.popBackStack() },
        titleBarSize = TitleBarSize.Large,
    ) {
        LabScreenImpl(paddingValues = it, navHostController)
    }
}

@Composable
fun LabScreenImpl(paddingValues: PaddingValues, navHostController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        TitleTextWithRipple(
            title = stringResource(id = R.string.lab_dynamic_icon),
            text = stringResource(id = R.string.lab_dynamic_icon_tips)
        ) {
        }
    }
}

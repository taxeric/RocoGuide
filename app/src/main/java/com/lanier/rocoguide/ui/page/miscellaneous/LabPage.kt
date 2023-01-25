package com.lanier.rocoguide.ui.page.miscellaneous

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.lanier.rocoguide.R
import com.lanier.rocoguide.base.ROUTE_SCREEN_LAB_DYNAMIC_ICON
import com.lanier.rocoguide.ui.common.CommonBaseScaffold
import com.lanier.rocoguide.ui.common.TitleBarSize
import com.lanier.rocoguide.ui.common.TitleTextWithRipple
import kotlinx.coroutines.delay

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
    var visibility by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = Unit) {
        delay(600)
        visibility = true
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        AnimatedVisibility(visible = visibility) {
            Box(
                modifier = Modifier
                    .height(32.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(12.dp, 0.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.lab_features_tips),
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart)
                )
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable { visibility = false }
                )
            }
        }
        TitleTextWithRipple(
            title = stringResource(id = R.string.lab_dynamic_icon),
            text = stringResource(id = R.string.lab_dynamic_icon_tips)
        ) {
            navHostController.navigate(ROUTE_SCREEN_LAB_DYNAMIC_ICON)
        }
    }
}

package com.lanier.rocoguide.ui.page

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.lanier.rocoguide.ui.common.EnableBackBaseScaffold

/**
 * Create by Eric
 * on 2022/12/15
 */
@Composable
fun EnvironmentScreen(navHostController: NavHostController, title: String) {
    EnableBackBaseScaffold(
        title = title,
        onBack = { navHostController.popBackStack() },
    ) {
    }
}
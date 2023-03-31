package com.lanier.rocoguide.ui.page.miscellaneous

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.lanier.rocoguide.ui.common.EnableBackBaseScaffold

/**
 * Create by Eric
 * on 2023/3/31
 */
@Composable
fun BGMScreen(navHostController: NavHostController, title: String) {
    EnableBackBaseScaffold(
        title = title,
        onBack = { navHostController.popBackStack() },
    ) {
        BGMScreenImpl(it)
    }
}

@Composable
private fun BGMScreenImpl(
    paddingValues: PaddingValues
) {
}
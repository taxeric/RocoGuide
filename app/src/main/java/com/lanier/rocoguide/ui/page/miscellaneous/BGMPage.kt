package com.lanier.rocoguide.ui.page.miscellaneous

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.lanier.rocoguide.R
import com.lanier.rocoguide.entity.MusicEntity
import com.lanier.rocoguide.service.music.LocalSongController
import com.lanier.rocoguide.ui.common.EnableBackBaseScaffold
import com.lanier.rocoguide.ui.common.baseBackground
import com.lanier.rocoguide.vm.music.MusicVMFactory
import com.lanier.rocoguide.vm.music.MusicViewModel

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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .baseBackground()
            .padding(paddingValues)
    ) {
    }
}

@Composable
private fun BGMItem(
    onPlay: () -> Unit,
    onDownload: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "name",
            color = Color.Black,
            modifier = Modifier
                .weight(5f)
                .padding(horizontal = 8.dp)
        )
        IconButton(
            onClick = {  },
            modifier = Modifier
                .weight(1f)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_play_filled_rounded),
                contentDescription = ""
            )
        }
        IconButton(
            onClick = {  },
            modifier = Modifier
                .weight(1f)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_download_24),
                contentDescription = ""
            )
        }
    }
}
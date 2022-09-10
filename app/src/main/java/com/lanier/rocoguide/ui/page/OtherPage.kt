package com.lanier.rocoguide.ui.page

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.lanier.lib_net.RetrofitHelper
import com.lanier.rocoguide.R
import com.lanier.rocoguide.base.ROUTE_SCREEN_MAIN_GENETIC
import com.lanier.rocoguide.base.ROUTE_SCREEN_MAIN_PERSONALITY
import com.lanier.rocoguide.base.ROUTE_SCREEN_MAIN_SETTINGS
import com.lanier.rocoguide.base.ROUTE_SCREEN_MAIN_SKILL_LIST
import com.lanier.rocoguide.base.cache.LocalCache
import com.lanier.rocoguide.service.DownloadService
import com.lanier.rocoguide.ui.common.*
import com.lanier.rocoguide.utils.NotificationUtil
import com.lanier.rocoguide.utils.installApk

/**
 * Create by Eric
 * on 2022/7/25
 */
@Composable
fun OtherScreen(navHostController: NavHostController, title: String){
    CommonBaseScaffold(title = title, actions = {
        IconButton(onClick = {
            navHostController.navigate(ROUTE_SCREEN_MAIN_SETTINGS)
        }) {
            Icon(imageVector = Icons.Outlined.Settings, contentDescription = "")
        }
    }) {
        OthersMain(navHostController, padding = it)
    }
}

@Composable
fun OthersMain(navHostController: NavHostController, padding: PaddingValues){
    Column(modifier = Modifier.padding(padding)) {
        OtherCS(navHostController)
    }
}

@Composable
fun OtherCS(navHostController: NavHostController){
    Column(modifier = Modifier.fillMaxWidth()) {
        SingleTitle(title = "CS", dividerHeight = 0.5f)
        OtherHorizontalItem("技能大全") {
            navHostController.navigate(ROUTE_SCREEN_MAIN_SKILL_LIST)
        }
        OtherHorizontalItem("性格修正") {
            navHostController.navigate(ROUTE_SCREEN_MAIN_PERSONALITY)
        }
        OtherHorizontalItem("遗传图鉴") {
            navHostController.navigate(ROUTE_SCREEN_MAIN_GENETIC)
        }
    }
}

@Composable
private fun OtherHorizontalItem(title: String, click: () -> Unit = {}){
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            click()
        },
        verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.ic_gulu_base_bg_1), contentDescription = "",
                modifier = Modifier.size(24.dp))
        }
        Text(text = title, modifier = Modifier
            .weight(9f)
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp))
    }
}

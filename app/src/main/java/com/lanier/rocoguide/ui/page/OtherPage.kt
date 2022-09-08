package com.lanier.rocoguide.ui.page

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.lanier.rocoguide.R
import com.lanier.rocoguide.base.ROUTE_SCREEN_MAIN_GENETIC
import com.lanier.rocoguide.base.ROUTE_SCREEN_MAIN_PERSONALITY
import com.lanier.rocoguide.base.ROUTE_SCREEN_MAIN_SKILL_LIST
import com.lanier.rocoguide.base.cache.LocalCache
import com.lanier.rocoguide.service.DownloadService
import com.lanier.rocoguide.ui.common.ChangeLogDialog
import com.lanier.rocoguide.ui.common.CommonBaseScaffold
import com.lanier.rocoguide.ui.common.SingleTitle
import com.lanier.rocoguide.ui.common.TitleText
import com.lanier.rocoguide.utils.NotificationUtil

/**
 * Create by Eric
 * on 2022/7/25
 */
@Composable
fun OtherScreen(navHostController: NavHostController, title: String){
    CommonBaseScaffold(title = title) {
        OthersMain(navHostController, padding = it)
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
        SingleTitle(title = "CS", 0.5f)
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
fun OtherDT(navHostController: NavHostController){
    Column(modifier = Modifier.fillMaxWidth()) {
        SingleTitle(title = "DT", 0.5f)
        GlanceTips()
        VersionText()
        AboutText()
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

@Composable
fun GlanceTips(){
    TitleText(title = "小部件", text = stringResource(id = R.string.glance_tips),
        titleSize = 16.sp,
        titleWeight = null,
        textColor = MaterialTheme.colorScheme.outline,)
}

@Composable
fun AboutText(){
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    TitleText("关于",
        stringResource(id = R.string.about),
        titleSize = 16.sp,
        titleWeight = null,
        textColor = MaterialTheme.colorScheme.outline
    ) {
        clipboardManager.setText(buildAnnotatedString { append("LBA2460") })
        Toast.makeText(context, "Copied!", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun VersionText(){
    val context = LocalContext.current
    val pi = context.packageManager.getPackageInfo(context.packageName, 0)
    var showUpdateDialog by remember {
        mutableStateOf(false)
    }
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.background)
        .clickable {
            showUpdateDialog = true
        }
        .padding(10.dp)
    ) {
        val (idKey, idValue) = createRefs()
        Text(text = "版本", modifier = Modifier.constrainAs(idKey) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        })
        Text(text = pi.versionName, fontSize = 14.sp,
            color = MaterialTheme.colorScheme.surfaceTint,
            modifier = Modifier.constrainAs(idValue) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
        })
    }
    if (showUpdateDialog) {
        val cache = LocalCache.newestData
        if (cache.isNewestVersion) {
            ChangeLogDialog(cache.log, cache.url, cache.mandatory, cache.size, cache.isDownloading,
                onPositiveEvent = {
//                    LocalCache.newestData = LocalCache.newestData.copy(isDownloading = true)
                    //下载
                    val testUrl = "http://xxx/res/apk/xxx.apk"
//                    LocalCache.newestData = LocalCache.newestData.copy(isDownloading = true)
                    NotificationUtil.makeNotification()
                    context.startService(
                        Intent(context, DownloadService::class.java)
                        .putExtra(DownloadService.PARAMS_URL, testUrl))
                }) {
                showUpdateDialog = false
            }
        } else {
            showUpdateDialog = false
        }
    }
}

package com.lanier.rocoguide.ui.page.settings

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.lanier.lib_net.RetrofitHelper
import com.lanier.rocoguide.R
import com.lanier.rocoguide.base.ROUTE_SCREEN_THANKS
import com.lanier.rocoguide.base.cache.LocalCache
import com.lanier.rocoguide.service.DownloadService
import com.lanier.rocoguide.ui.common.*
import com.lanier.rocoguide.ui.theme.DarkMode
import com.lanier.rocoguide.ui.theme.LocalDarkTheme
import com.lanier.rocoguide.ui.theme.icons.FolderOpen
import com.lanier.rocoguide.utils.NotificationUtil

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/9/8 17:23
 * Desc  :
 */
@Composable
fun SettingsScreen(navHostController: NavHostController, title: String){
    CommonBaseScaffold(title = title,
        onBack = { navHostController.popBackStack() },
        showNavigationIcon = true,
        titleBarSize = TitleBarSize.Large) {
        SettingsScreenImpl(navHostController = navHostController, paddingValues = it)
    }
}

@Composable
fun SettingsScreenImpl(navHostController: NavHostController, paddingValues: PaddingValues){
    Column(modifier = Modifier
        .padding(paddingValues)
        .verticalScroll(rememberScrollState())
    ) {
        PreferenceSubtitle(text = "Preferences")
        DarkTheme()
        DownloadNewestVersionPath()
        PreferenceSubtitle(text = "Others")
        VersionText()
        GlanceTips()
        ReadMe()
        Thank(navHostController)
        AboutText()
    }
}

@Composable
fun DownloadNewestVersionPath(){
    PreferenceItem(
        title = "更新路径",
        description = "设定新版本安装包下载路径,默认为本地缓存路径,随应用删除而删除",
        icon = Icons.Outlined.FolderOpen,
        enabled = false
    )
}

@Composable
fun DarkTheme(){
    var showDarkDialog by remember {
        mutableStateOf(false)
    }
    PreferenceItem(
        title = "暗色模式" ,
        description = LocalDarkTheme.current.getDarkThemeDesc(),
        icon = Icons.Outlined.DarkMode,
    ){
        showDarkDialog = true
    }
    if (showDarkDialog) {
        DarkThemeDialog {
            showDarkDialog = false
        }
    }
}

@Composable
fun ReadMe(){
    val context = LocalContext.current
    val url = "https://gitee.com/lanier/roco-guide"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    TitleTextWithRipple(title = "README", text = stringResource(id = R.string.readme)) {
        context.startActivity(intent)
    }
}

@Composable
fun Thank(navHostController: NavHostController){
    TitleTextWithRipple(title = "鸣谢", text = stringResource(id = R.string.thank)){
        navHostController.navigate(ROUTE_SCREEN_THANKS)
    }
}

@Composable
fun GlanceTips(){
    TitleTextWithRipple(title = "小部件", text = stringResource(id = R.string.glance_tips))
}

@Composable
fun AboutText(){
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    TitleTextWithRipple("关于", stringResource(id = R.string.about)) {
        clipboardManager.setText(buildAnnotatedString { append("TEJBMjQ2MA") })
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
    TitleTextWithRipple("版本", text = pi.versionName) {
        showUpdateDialog = true
    }
    if (showUpdateDialog) {
        val cache = LocalCache.newestData
        if (cache.isNewestVersion) {
            ChangeLogDialog(cache.log, cache.url, cache.mandatory, cache.size, cache.isDownloading,
                onPositiveEvent = {
                    //下载
                    val url = "${RetrofitHelper.baseUrl}$it"
                    NotificationUtil.makeNotification()
                    context.startService(
                        Intent(context, DownloadService::class.java)
                            .putExtra(DownloadService.PARAMS_URL, url))
                }) {
                showUpdateDialog = false
            }
        } else {
            showUpdateDialog = false
        }
    }
}

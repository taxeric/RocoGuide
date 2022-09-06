package com.lanier.rocoguide.ui.common

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.activity.ComponentActivity
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lanier.rocoguide.base.*
import com.lanier.rocoguide.base.cache.LocalCache
import com.lanier.rocoguide.utils.logE
import com.lanier.rocoguide.vm.ChangeLogViewModel

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/9/5 15:36
 * Desc  :
 */
@Composable
fun UpdateView(){
    val vm = viewModel<ChangeLogViewModel>()
    val context = LocalContext.current
    var showUpdateDialog by remember {
        mutableStateOf(false)
    }
    var log by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    var mandatory by remember { mutableStateOf(false) }
    var size by remember { mutableStateOf("") }
    LaunchedEffect(key1 = "checkUpdate") {
        vm.getNewestVersion(context)
    }
    ChangeLog { mLog, mUrl, mMandatory, mSize ->
        log = mLog
        url = mUrl
        mandatory = mMandatory
        size = mSize
        LocalCache.newestData = LocalCache.newestData.copy(
            log = mLog,
            url = mUrl,
            mandatory = mMandatory,
            size = mSize,
            isNewestVersion = true
        )
        showUpdateDialog = true
    }
    if (showUpdateDialog) {
        ChangeLogDialog(log, url, mandatory, size, LocalCache.newestData.isDownloading,
            onPositiveEvent = {
                LocalCache.newestData = LocalCache.newestData.copy(isDownloading = true)
                //下载
            },
            onNegativeEvent = {isMandatory, isDownloading ->
                //如果是强制更新则退出,否则只dis对话框
                if (isMandatory) {
                    (context as ComponentActivity).finish()
                }
            }
        ){
            showUpdateDialog = false
        }
    }
}

@Composable
fun ChangeLog(onChangeLog: (String, String, Boolean, String) -> Unit){
    val context = LocalContext.current
    DisposableEffect(key1 = "version") {
        val filter = IntentFilter(ACTION_CHANGE_LOG_VERSION)
        val receiver = object : BroadcastReceiver(){
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == ACTION_CHANGE_LOG_VERSION) {
                    val log = intent.getStringExtra(ACTION_PARAMS_LOG)?: ""
                    val url = intent.getStringExtra(ACTION_PARAMS_URL)?: ""
                    val mandatory = intent.getBooleanExtra(ACTION_PARAMS_MANDATORY, false)
                    val size = intent.getStringExtra(ACTION_PARAMS_SIZE)?: "--"
                    if (log.isNotEmpty() && url.isNotEmpty()) {
                        onChangeLog(log, url, mandatory, size)
                    }
                }
            }
        }
        "register receiver".logE()
        context.registerReceiver(receiver, filter)
        onDispose {
            "unregister receiver".logE()
            context.unregisterReceiver(receiver)
        }
    }
}

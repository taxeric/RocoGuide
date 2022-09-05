package com.lanier.rocoguide.vm

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanier.lib_net.RetrofitHelper
import com.lanier.rocoguide.BuildConfig
import com.lanier.rocoguide.base.*
import com.lanier.rocoguide.base.net.Net
import com.lanier.rocoguide.utils.logE
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/9/5 15:35
 * Desc  :
 */
class ChangeLogViewModel: ViewModel() {

    fun getNewestVersion(context: Context){
        "获取数据".logE()
        viewModelScope.launch {
            RetrofitHelper
                .launch {
                    if (BuildConfig.DEBUG) {
                        delay(3000)
                    }
                    Net.service.getNewestVersion()
                }
                .onSuccess {
                    if (it.code == 200) {
                        val remoteNewestVersion = it.data.versionCode
                        val localVersion = context.packageManager.getPackageInfo(context.packageName, 0).longVersionCode
                        if (remoteNewestVersion > localVersion || remoteNewestVersion < localVersion) {
                            val intent = Intent(ACTION_CHANGE_LOG_VERSION).apply {
                                putExtra(ACTION_PARAMS_LOG, it.data.log)
                                putExtra(ACTION_PARAMS_URL, it.data.url)
                                putExtra(ACTION_PARAMS_MANDATORY, it.data.mandatory)
                                putExtra(ACTION_PARAMS_SIZE, it.data.size)
                            }
                            context.sendBroadcast(intent)
                        }
                    }
                }
                .onFailure {
                    "get newest version failed -> ${it.message}".logE()
                    //do nothing
                }
        }
    }
}
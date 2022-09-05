package com.lanier.plugin_base

import android.util.Log

/**
 * Author: Eric
 * Email : 1248389474@qq.com
 * Date  : 2022/7/25 11:41
 * Desc  :
 */
private const val TAG = "PLUGIN_BASE"

internal fun String.logI(){
    Log.i(TAG, this)
}

internal fun String.logI(tag: String){
    Log.i(tag, this)
}

internal fun String.logE(){
    Log.e(TAG, this)
}

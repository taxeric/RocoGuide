package com.lanier.rocoguide.utils

import android.util.Log

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/9/5 15:22
 * Desc  :
 */
private const val TAG = "TAG_ROCO_GUIDE"

internal fun String.logI(tag: String = TAG){
    Log.i(tag, this)
}

internal fun String.logE(tag: String = TAG){
    Log.e(tag, this)
}

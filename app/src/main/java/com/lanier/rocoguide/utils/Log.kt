package com.lanier.rocoguide.utils

import android.util.Log

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/9/5 15:22
 * Desc  :
 */
private const val TAG = "TAG_ROCO_GUIDE"

fun String.logI(){
    Log.i(TAG, this)
}

fun String.logE(){
    Log.e(TAG, this)
}

fun String.logI(tag: String){
    Log.i(tag, this)
}

fun String.logE(tag: String){
    Log.e(tag, this)
}

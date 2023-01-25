package com.lanier.rocoguide.utils

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

/**
 * Create by Eric
 * on 2023/1/25
 */
object DynamicIconUtil {

    fun replaceIcon(name: String, defComponentName: ComponentName, context: Context) {
        context.packageManager.setComponentEnabledSetting(
            defComponentName,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
        context.packageManager.setComponentEnabledSetting(
            ComponentName(context, name),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
        reStartApp(context)
    }

    private fun reStartApp(context: Context) {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        val resolveInfos = context.packageManager.queryIntentActivities(intent, 0)
        for (resolveInfo in resolveInfos) {
            if (resolveInfo.activityInfo != null) {
                am.killBackgroundProcesses(resolveInfo.activityInfo.packageName)
            }
        }
    }
}
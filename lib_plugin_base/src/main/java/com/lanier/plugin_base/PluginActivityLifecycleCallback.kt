package com.lanier.plugin_base

import android.app.Activity
import android.app.Application
import android.app.Instrumentation
import android.os.Bundle

/**
 * Author: Eric
 * Email : 1248389474@qq.com
 * Date  : 2022/7/25 14:11
 * Desc  :
 */
class PluginActivityLifecycleCallback: Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
/*        val mInstrumentation = Activity::class.java.getDeclaredField("mInstrumentation")
        mInstrumentation.isAccessible = true
        val originInstrumentation = mInstrumentation.get(activity) as Instrumentation
        mInstrumentation.set(activity, PluginInstrumentation(originInstrumentation))*/
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }
}
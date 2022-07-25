package com.lanier.plugin_base

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

/**
 * Author: Eric
 * Email : 1248389474@qq.com
 * Date  : 2022/7/25 11:38
 * Desc  :
 */
abstract class IPluginActivityInterface {

    var lifecycle: Lifecycle? = null
    private val defaultLifecycleObserver = object : DefaultLifecycleObserver{

        override fun onCreate(owner: LifecycleOwner) {
            super.onCreate(owner)
            "插件 -> create".logI()
        }

        override fun onStart(owner: LifecycleOwner) {
            super.onStart(owner)
            "插件 -> start".logI()
        }

        override fun onResume(owner: LifecycleOwner) {
            super.onResume(owner)
            "插件 -> resume".logI()
        }

        override fun onPause(owner: LifecycleOwner) {
            super.onPause(owner)
            "插件 -> pause".logI()
        }

        override fun onStop(owner: LifecycleOwner) {
            super.onStop(owner)
            "插件 -> stop".logI()
        }

        override fun onDestroy(owner: LifecycleOwner) {
            super.onDestroy(owner)
            "插件 -> destroy".logI()
        }
    }

    lateinit var mHostActivity: HostActivity
    fun registerHostActivity(hostActivity: HostActivity) {
        mHostActivity = hostActivity;
    }

    fun getIntent() = mHostActivity.intent

    open fun onCreate(savedInstanceState: Bundle?){
        lifecycle?.addObserver(defaultLifecycleObserver)
    }

    open fun onDestroy(){
        lifecycle?.removeObserver(defaultLifecycleObserver)
    }

    fun setContentView(layoutResID: Int) {
        mHostActivity.setContentView(layoutResID)
    }

    protected open fun setContent(
        parentContext: CompositionContext? = null,
        content:  @Composable () -> Unit
    ){
        mHostActivity.setContent(parentContext, content)
    }
}

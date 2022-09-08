package com.lanier.rocoguide

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.lanier.rocoguide.base.cache.LocalCache
import com.lanier.rocoguide.service.DownloadService
import com.lanier.rocoguide.ui.common.UpdateView
import com.lanier.rocoguide.ui.page.MainHome
import com.lanier.rocoguide.ui.theme.RocoGuideTheme
import com.lanier.rocoguide.utils.NotificationUtil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RocoGuideTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainHome()
                }
                UpdateView {
                    val testUrl = "http://xxx/res/apk/xxx.apk"
                    NotificationUtil.makeNotification()
                    startService(Intent(this, DownloadService::class.java)
                        .putExtra(DownloadService.PARAMS_URL, testUrl))
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

//@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RocoGuideTheme {
        Greeting("Android")
    }
}
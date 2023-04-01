package com.lanier.rocoguide

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.lanier.lib_net.RetrofitHelper
import com.lanier.rocoguide.base.cache.CurrentDownloadContent
import com.lanier.rocoguide.base.cache.LocalCache
import com.lanier.rocoguide.entity.MusicEntity
import com.lanier.rocoguide.service.DownloadService
import com.lanier.rocoguide.service.music.IMusicController
import com.lanier.rocoguide.service.music.MusicControlAction
import com.lanier.rocoguide.ui.common.UpdateView
import com.lanier.rocoguide.ui.page.MainHome
import com.lanier.rocoguide.ui.theme.LocalDarkTheme
import com.lanier.rocoguide.ui.theme.RocoGuideTheme
import com.lanier.rocoguide.ui.theme.SettingsProvider
import com.lanier.rocoguide.utils.NotificationUtil
import com.lanier.rocoguide.vm.music.MusicVMFactory
import com.lanier.rocoguide.vm.music.MusicViewModel

class MainActivity : ComponentActivity() {

    private val musicVM by viewModels<MusicViewModel>(
        factoryProducer = {
            MusicVMFactory(this)
        }
    )
    private val songController = object : IMusicController {
        override fun setPlayList(music: MusicEntity) {
        }

        override fun play(music: MusicEntity) {
            musicVM.dispatch(
                MusicControlAction.Play(music)
            )
        }

        override fun resume() {
            musicVM.dispatch(
                MusicControlAction.Resume
            )
        }

        override fun pause() {
            musicVM.dispatch(
                MusicControlAction.Pause
            )
        }

        override fun stop(withRelease: Boolean) {
            musicVM.dispatch(
                MusicControlAction.Stop
            )
        }

        override fun release() {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SettingsProvider(songController) {
                val darkTheme = LocalDarkTheme.current.isDarkTheme()
                RocoGuideTheme(useDarkTheme = darkTheme) {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MainHome()
                    }
                    UpdateView {
                        val url = "${RetrofitHelper.baseUrl}$it"
                        NotificationUtil.makeNotification()
                        LocalCache.currentDownloadContent.tryEmit(
                            CurrentDownloadContent.UpdateApk
                        )
                        startService(
                            Intent(this, DownloadService::class.java)
                                .putExtra(DownloadService.PARAMS_URL, url)
                        )
                    }
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
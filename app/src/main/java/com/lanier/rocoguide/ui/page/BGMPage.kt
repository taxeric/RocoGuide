package com.lanier.rocoguide.ui.page

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.lanier.rocoguide.base.cache.LocalCache
import com.lanier.rocoguide.entity.RemoteMusicEntity
import com.lanier.rocoguide.service.music.MusicService
import com.lanier.rocoguide.ui.common.EnableBackBaseScaffold
import com.lanier.rocoguide.ui.theme.ExtendedTheme

/**
 * Create by Eric
 * on 2022/9/12
 */
@Composable
fun BGMScreen(navHostController: NavHostController, title: String){
    EnableBackBaseScaffold(title, onBack = {
        navHostController.popBackStack()
    }) {
        BGMScreenImpl(it)
    }
}

@Composable
fun BGMScreenImpl(paddingValues: PaddingValues){
    Column(modifier = Modifier
        .padding(paddingValues)
        .background(ExtendedTheme.colors.defaultMainBackground)) {
        BGMScreenList()
    }
}

@Composable
fun BGMScreenList(){
    val list = mutableListOf<RemoteMusicEntity>().apply {
        add(RemoteMusicEntity("res/mp3/bllc.mp3", "白落落村"))
        add(RemoteMusicEntity("res/mp3/cwy.mp3", "宠物园"))
        add(RemoteMusicEntity("res/mp3/fxpb.mp3", "枫雪瀑布"))
        add(RemoteMusicEntity("res/mp3/esdh.mp3", "儿时的画"))
    }
    LazyColumn {
        itemsIndexed(list) {index, item ->
            BGMScreenItem(music = item)
        }
    }
}

@Composable
fun BGMScreenItem(music: RemoteMusicEntity){
    val context = LocalContext.current
    Text(text = music.name, modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clickable {
            LocalCache.bgmList.clear()
            LocalCache.bgmList.add(music)
            context.startService(
                Intent(context, MusicService::class.java)
            )
        })
}
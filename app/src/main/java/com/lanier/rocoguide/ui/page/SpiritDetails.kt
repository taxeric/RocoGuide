package com.lanier.rocoguide.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.lanier.rocoguide.R
import com.lanier.rocoguide.entity.SpiritEntity

/**
 * Create by Eric
 * on 2022/7/26
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpiritScreen(navHostController: NavHostController, spiritId: Int){
    val title = ""
    val layEggsEnable = true
    val eggGroup = ""
    val eggGroupId = ""
    var showEggDialog by remember {
        mutableStateOf(false)
    }
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            SmallTopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                    }
                },
                actions = {
                    if (layEggsEnable){
                        IconButton(onClick = {
                            showEggDialog = true
                        }) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_lay_egg),
                                contentDescription = "egg",
                                modifier = Modifier.clip(RoundedCornerShape(50.dp))
                            )
                        }
                    }
                }
            )
        },
    ) { innerPadding ->
        SpiritDetailData(innerPadding)
    }
    if (showEggDialog){
        EggDialog {
            showEggDialog = false
        }
    }
}

@Composable
fun SpiritDetailData(paddingValues: PaddingValues){
    SpiritDetailImpl(paddingValues, SpiritEntity(
        primary_attributes_id = 1,
        secondary_attributes_id = 2,
        name = "lalala",
        description = "火花，腾讯游戏《洛克王国》初始宠物，可以自然进化为焰火、火神，火神又可依次超进化为烈火战神、豪炎战神，拥有全新蛋生纪念皮肤公测火花。长大可以喷出火焰，现在已经可以熟练使用火花。"))
}

@Composable
fun SpiritDetailImpl(paddingValues: PaddingValues, data: SpiritEntity){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(paddingValues)) {
        Row {
            SpiritEntityPic(data, modifier = Modifier.weight(0.35f))
            SpiritEntityBaseInfo(data, modifier = Modifier.weight(0.65f))
        }
        Spacer(modifier = Modifier.height(10.dp))
//        TODO("六项种族值")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpiritEntityPic(data: SpiritEntity, modifier: Modifier){
    Card(elevation = CardDefaults.cardElevation(defaultElevation = 5.dp), modifier = modifier) {
        Column(modifier = Modifier.height(200.dp)) {
            AsyncImage(model = data.avatar, contentDescription = "avatar", modifier = Modifier.height(170.dp))
            Box(modifier = Modifier.height(30.dp).background(Color.Yellow)) {
                Row(modifier = Modifier.fillMaxHeight().width(30.dp).background(Color.Blue)) {
                    if (data.primary_attributes_id != 0){
                        Image(painter = painterResource(id = R.drawable.ic_jelly), contentDescription = "pr")
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                    if (data.secondary_attributes_id != 0){
                        Image(painter = painterResource(id = R.drawable.ic_jelly), contentDescription = "dm")
                    }
                }
                Image(painter = painterResource(id = R.drawable.ic_jelly),
                    contentDescription = "",
                    modifier = Modifier.fillMaxHeight().width(30.dp).background(Color.Green).align(Alignment.CenterEnd))
            }
        }
    }
}

@Composable
fun SpiritEntityBaseInfo(data: SpiritEntity, modifier: Modifier){
    Column(modifier = modifier) {
        Text(text = data.name)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = data.description)
    }
}

@Composable
fun EggDialog(onDismiss: () -> Unit){
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        )
    ) {
        Column(modifier = Modifier.background(Color.White).clip(RoundedCornerShape(30.dp))) {
            Text(text = "敬请期待", modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(20.dp))
            TextButton(onClick = onDismiss, modifier = Modifier
                .align(Alignment.End)
                .padding(5.dp)) {
                Text(text = "确定")
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

package com.lanier.rocoguide.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
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
            SpiritEntityPic(data, modifier = Modifier
                .weight(0.35f)
                .padding(10.dp, 5.dp))
            SpiritEntityBaseInfo(data, modifier = Modifier
                .weight(0.65f)
                .padding(10.dp, 5.dp))
        }
        Spacer(modifier = Modifier.height(10.dp))
        SpiritRacialValue(data)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpiritEntityPic(data: SpiritEntity, modifier: Modifier){
    Card(elevation = CardDefaults.cardElevation(defaultElevation = 5.dp), modifier = modifier.background(Color.White)) {
        Column(modifier = Modifier.height(200.dp)) {
            AsyncImage(model = data.avatar, contentDescription = "avatar", modifier = Modifier.height(170.dp))
            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val (attr1, attr2, space, eggGroup) = createRefs()
                if (data.primary_attributes_id != 0){
                    Image(
                        painter = painterResource(id = R.drawable.ic_jelly),
                        contentDescription = "pr",
                        modifier = Modifier.constrainAs(attr1){
                            start.linkTo(parent.start)
                        }
                    )
                    Spacer(modifier = Modifier
                        .width(10.dp)
                        .constrainAs(space) {
                            start.linkTo(attr1.end)
                        })
                }
                if (data.secondary_attributes_id != 0){
                    Image(
                        painter = painterResource(id = R.drawable.ic_jelly),
                        contentDescription = "dm",
                        modifier = Modifier.constrainAs(attr2){
                            start.linkTo(space.end)
                        }
                    )
                }
                Image(painter = painterResource(id = R.drawable.ic_jelly),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(30.dp)
                        .constrainAs(eggGroup) {
                            end.linkTo(parent.end)
                        })
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

@Preview(backgroundColor = 0xffffffff)
@Composable
fun SpiritRacialValue(data: SpiritEntity = SpiritEntity()){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp, 5.dp)) {
        SpiritSingleRacialValue(modifier = Modifier.wrapContentHeight().weight(1f), )
        SpiritSingleRacialValue(modifier = Modifier.wrapContentHeight().weight(1f), )
        SpiritSingleRacialValue(modifier = Modifier.wrapContentHeight().weight(1f), )
        SpiritSingleRacialValue(modifier = Modifier.wrapContentHeight().weight(1f), )
        SpiritSingleRacialValue(modifier = Modifier.wrapContentHeight().weight(1f), )
        SpiritSingleRacialValue(modifier = Modifier.wrapContentHeight().weight(1f), )
    }
}

@Composable
fun SpiritSingleRacialValue(modifier: Modifier = Modifier, name: String = "种族", value: Int = 100){
    Column(modifier = modifier
        .fillMaxWidth()
        .background(Color.White)
        .border(1.dp, Color(0xFF7961FE))) {
        Text(text = name, color = Color(0xFFC3C6E0), fontSize = 18.sp, textAlign = TextAlign.Center, modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xff61A9FE))
            .padding(10.dp))
        Text(text = "$value", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()
            .padding(4.dp))
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
        Column(modifier = Modifier
            .background(Color.White)
            .clip(RoundedCornerShape(30.dp))) {
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

package com.lanier.rocoguide.ui.page

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.lanier.rocoguide.R
import com.lanier.rocoguide.entity.Skill
import com.lanier.rocoguide.entity.SpiritAttributes
import com.lanier.rocoguide.entity.SpiritEntity
import com.lanier.rocoguide.ui.common.DataErrorDialog
import com.lanier.rocoguide.ui.common.EggDialog
import com.lanier.rocoguide.ui.common.SkillDialog
import com.lanier.rocoguide.vm.SpiritDetailViewModel

/**
 * Create by Eric
 * on 2022/7/26
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpiritScreen(navHostController: NavHostController, spiritId: Int){
    val vm = viewModel<SpiritDetailViewModel>()
    val spirit = vm.spiritDetail.collectAsState().value
    val id = spirit.id
    val title = spirit.name
    val layEggsEnable = spirit.group.id != 0
    val eggGroup = spirit.group.name
    val eggGroupId = spirit.group.id
    var showEggDialog by remember {
        mutableStateOf(layEggsEnable)
    }
    var showErrorDialog by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = Unit) {
        vm.getSpiritById(spiritId)
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
                    if (id == -1) {
                        IconButton(onClick = {
                            showErrorDialog = true
                        }) {
                            Icon(imageVector = Icons.Filled.Warning, contentDescription = "warning")
                        }
                    }
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
        SpiritDetailData(innerPadding, navHostController, spirit)
    }
    if (showErrorDialog) {
        DataErrorDialog(type = spirit.id) {
            showErrorDialog = false
        }
    }
    if (showEggDialog) {
        EggDialog {
            showEggDialog = false
        }
    }
}

@Composable
fun SpiritDetailData(paddingValues: PaddingValues, navHostController: NavHostController, spirit: SpiritEntity){
    SpiritDetailImpl(paddingValues, spirit, navHostController)
}

@Composable
fun SpiritDetailImpl(paddingValues: PaddingValues, data: SpiritEntity, navHostController: NavHostController){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(paddingValues)
        .verticalScroll(rememberScrollState())) {
        Row {
            SpiritEntityPic(data, modifier = Modifier
                .weight(0.35f)
                .padding(10.dp, 5.dp))
            SpiritEntityBaseInfo(data, modifier = Modifier
                .weight(0.65f)
                .padding(10.dp, 5.dp))
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = data.description, color = Color.Black, fontSize = 18.sp, modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp))
        Spacer(modifier = Modifier.height(10.dp))
        SpiritRacialValue(data)
        Spacer(modifier = Modifier.height(10.dp))
        SpiritSkills(data, navHostController)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = buildAnnotatedString {
            append("数据来自网络,如有纰漏请")
            withStyle(SpanStyle(color = Color.Blue)) {
                append("联系改正")
            }
        }, textAlign = TextAlign.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp))
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
                if (data.primaryAttributes.id != 0){
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
                if (data.secondaryAttributes.id != 0){
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
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "身高: ${data.height}m", modifier = Modifier.weight(1f))
            Text(text = "体重: ${data.weight}kg", modifier = Modifier.weight(1f))
        }
        Text(text = data.hobby)
    }
}

//@Preview(backgroundColor = 0xffffffff)
@Composable
fun SpiritRacialValue(data: SpiritEntity = SpiritEntity()){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp, 5.dp)) {
        SpiritSingleRacialValue(modifier = Modifier
            .wrapContentHeight()
            .weight(1f), "精力", data.racePower)
        SpiritSingleRacialValue(modifier = Modifier
            .wrapContentHeight()
            .weight(1f), "攻击", data.raceAttack)
        SpiritSingleRacialValue(modifier = Modifier
            .wrapContentHeight()
            .weight(1f), "防御", data.raceDefense)
        SpiritSingleRacialValue(modifier = Modifier
            .wrapContentHeight()
            .weight(1f), "魔攻", data.raceMagicAttack)
        SpiritSingleRacialValue(modifier = Modifier
            .wrapContentHeight()
            .weight(1f), "魔抗", data.raceMagicDefense)
        SpiritSingleRacialValue(modifier = Modifier
            .wrapContentHeight()
            .weight(1f), "速度", data.raceSpeed)
    }
}

@Composable
fun SpiritSingleRacialValue(modifier: Modifier = Modifier, name: String = "??", value: Int = 100){
    Column(modifier = modifier
        .fillMaxWidth()
        .background(Color.White)
        .border(1.dp, Color(0xFF83AAF7))) {
        Text(text = name, color = Color(0xFFEEEEEE), fontSize = 18.sp, textAlign = TextAlign.Center, modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF90C3FF))
            .padding(10.dp))
        Text(text = "$value", textAlign = TextAlign.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp))
    }
}

@Composable
fun SpiritSkills(data: SpiritEntity, navHostController: NavHostController){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp, 5.dp)
        .background(Color.White)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .border(0.5.dp, Color.Yellow)) {
            Text(text = "技能", textAlign = TextAlign.Center, modifier = Modifier.weight(1.2f))
            Text(text = "威力", textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
            Text(text = "PP", textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
            Text(text = "类型", textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
            Text(text = "附加效果", textAlign = TextAlign.Center, modifier = Modifier.weight(1.8f))
        }
        data.skills.forEach {
            SingleSkill(it, navHostController)
        }
    }
}

@Composable
fun SingleSkill(skill: Skill, navHostController: NavHostController){
    var showSkillDialog by remember {
        mutableStateOf(false)
    }
    var dialogContent by remember {
        mutableStateOf("")
    }
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            dialogContent = skill.description
            showSkillDialog = true
        }
    ) {
        Text(text = skill.name, fontSize = 13.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1.2f))
        Text(text = "${skill.value}", fontSize = 13.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
        Text(text = "${skill.amount}", fontSize = 13.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
        val type = skill.attributes.name + skill.skillType.name
        Text(text = type, fontSize = 13.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
        Text(text = skill.additional_effects, fontSize = 13.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1.8f))
    }
    if (showSkillDialog) {
        SkillDialog(dialogContent) {
            showSkillDialog = false
        }
    }
}

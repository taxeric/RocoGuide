package com.lanier.rocoguide.ui.page

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.lanier.rocoguide.R
import com.lanier.rocoguide.base.ROUTE_SCREEN_GENETIC_DETAIL
import com.lanier.rocoguide.base.ROUTE_SCREEN_SKILL_DETAIL
import com.lanier.rocoguide.entity.Skill
import com.lanier.rocoguide.entity.SpiritEntity
import com.lanier.rocoguide.ui.common.*
import com.lanier.rocoguide.vm.SpiritDetailViewModel

/**
 * Create by Eric
 * on 2022/7/26
 */
@Composable
fun SpiritScreen(navHostController: NavHostController, spiritId: Int){
    val vm = viewModel<SpiritDetailViewModel>()
    val spirit = vm.spiritDetail.collectAsState().value
    val id = spirit.id
    val title = spirit.name
    val layEggsEnable = spirit.group.id > 1
    val eggGroup = spirit.group.name
    val eggGroupId = spirit.group.id
    var showEggDialog by remember {
        mutableStateOf(false)
    }
    var showErrorDialog by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = Unit) {
        vm.getSpiritById(spiritId)
    }
    EnableBackBaseScaffoldWithActions(title = title, onBack = { navHostController.popBackStack() }, actions = {
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
    }) {
        SpiritDetailData(it, navHostController, spirit)
    }
    if (showErrorDialog) {
        DataErrorDialog(type = spirit.id) {
            showErrorDialog = false
        }
    }
    if (showEggDialog) {
        EggDialog(eggGroup, eggGroupId) {
            if (it) {
                navHostController.navigate("${ROUTE_SCREEN_GENETIC_DETAIL}/$eggGroupId/$eggGroup")
            }
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
        Text(text = data.description, fontSize = 18.sp, modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp))
        Spacer(modifier = Modifier.height(10.dp))
        SpiritRacialValue(data)
        Spacer(modifier = Modifier.height(10.dp))
        SpiritSkillsV2(data, navHostController)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = buildAnnotatedString {
            append("数据来自网络,如有纰漏请")
            withStyle(SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)) {
                append("联系改正")
            }
        }, textAlign = TextAlign.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp))
    }
}

@Composable
fun SpiritEntityPic(data: SpiritEntity, modifier: Modifier){
    Box(modifier = modifier
        .clip(RoundedCornerShape(5.dp))
        .background(Color.Yellow)) {
        Column(modifier = Modifier.height(200.dp)) {
            AsyncImage(model = data.avatar, contentDescription = "avatar", modifier = Modifier.height(170.dp))
            ConstraintLayout(modifier = Modifier
                .fillMaxSize()
                .padding(4.dp, 0.dp)) {
                val (attr1, attr2, space, eggGroup) = createRefs()
                if (data.primaryAttributes.id != 0){
                    AttrImage(attr = data.primaryAttributes, modifier = Modifier
                        .width(20.dp)
                        .constrainAs(attr1) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        })
                }
                if (data.secondaryAttributes.id != null && data.secondaryAttributes.id != 0){
                    Spacer(modifier = Modifier
                        .width(8.dp)
                        .constrainAs(space) {
                            start.linkTo(attr1.end)
                        })
                    AttrImage(attr = data.secondaryAttributes, modifier = Modifier
                        .width(20.dp)
                        .constrainAs(attr2) {
                            start.linkTo(space.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        })
                }
                EggGroupImage(eg = data.group, modifier = Modifier
                    .width(20.dp)
                    .constrainAs(eggGroup) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    })
            }
        }
    }
}

@Composable
fun SpiritEntityBaseInfo(data: SpiritEntity, modifier: Modifier){
    Column(modifier = modifier) {
//        Text(text = data.name, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "编号: ${data.number}")
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "身高: ${data.height}m", modifier = Modifier.weight(1f))
            Text(text = "体重: ${data.weight}kg", modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "爱好: ${data.hobby}")
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
            .weight(1f), R.drawable.ic_race_energy, data.racePower)
        SpiritSingleRacialValue(modifier = Modifier
            .wrapContentHeight()
            .weight(1f), R.drawable.ic_race_attack, data.raceAttack)
        SpiritSingleRacialValue(modifier = Modifier
            .wrapContentHeight()
            .weight(1f), R.drawable.ic_race_defense, data.raceDefense)
        SpiritSingleRacialValue(modifier = Modifier
            .wrapContentHeight()
            .weight(1f), R.drawable.ic_race_magic_attack, data.raceMagicAttack)
        SpiritSingleRacialValue(modifier = Modifier
            .wrapContentHeight()
            .weight(1f), R.drawable.ic_race_magic_defense, data.raceMagicDefense)
        SpiritSingleRacialValue(modifier = Modifier
            .wrapContentHeight()
            .weight(1f), R.drawable.ic_race_speed, data.raceSpeed)
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
fun SpiritSingleRacialValue(modifier: Modifier = Modifier, racePic: Int, value: Int = 100){
    Column(modifier = modifier
        .fillMaxWidth()
        .border(1.dp, Color(0xFF83AAF7))) {
        Image(painter = painterResource(id = racePic), contentDescription = "race_value", modifier = Modifier
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
        .padding(10.dp, 5.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .border(0.5.dp, Color(0xFF83AAF7))) {
            Text(text = "技能", textAlign = TextAlign.Center, modifier = Modifier.weight(1.2f))
            Text(text = "威力", textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
            Text(text = "PP", textAlign = TextAlign.Center, modifier = Modifier.weight(0.8f))
            Text(text = "类型", textAlign = TextAlign.Center, modifier = Modifier.weight(1.2f))
            Text(text = "附加效果", textAlign = TextAlign.Center, modifier = Modifier.weight(1.8f))
        }
        data.skills.forEach {
            SingleSkill(it, navHostController)
        }
    }
}

@Composable
fun SpiritSkillsV2(data: SpiritEntity, navHostController: NavHostController){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp, 5.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .border(0.3.dp, Color(0xFF83AAF7))) {
            Text(text = "技能", textAlign = TextAlign.Center, modifier = Modifier.weight(1.2f))
            Text(text = "威力", textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
            Text(text = "PP", textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
            Text(text = "描述", textAlign = TextAlign.Center, modifier = Modifier.weight(1.8f))
        }
        data.skills.forEachIndexed { index, skill ->
            SingleSkillV2(skill, navHostController)
        }
        Divider(color = Color(0xFF83AAF7), modifier = Modifier
            .fillMaxWidth()
            .height(0.5.dp))
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
        .padding(0.dp, 2.dp)
        .clickable {
            dialogContent = skill.description
            showSkillDialog = true
        }
    ) {
        Text(text = skill.name, fontSize = 13.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1.2f))
        Text(text = "${skill.value}", fontSize = 13.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
        Text(text = "${skill.amount}", fontSize = 13.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(0.8f))
        val type = skill.attributes.name + skill.skillType.name
        Text(text = type.replace("系", "-"), fontSize = 13.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1.2f))
        Text(text = skill.additional_effects, fontSize = 13.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1.8f))
    }
    if (showSkillDialog) {
        SkillDialog(dialogContent) {
            showSkillDialog = false
        }
    }
}

@Composable
fun SingleSkillV2(skill: Skill, navHostController: NavHostController){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .clickable {
            navHostController.navigate("$ROUTE_SCREEN_SKILL_DETAIL/${Gson().toJson(skill)}")
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(color = Color(0xFF83AAF7), modifier = Modifier
            .fillMaxHeight()
            .width(0.5.dp))
        Text(text = skill.name, fontSize = 13.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1.2f))
        Text(text = "${skill.value}", fontSize = 13.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
        Text(text = "${skill.amount}", fontSize = 13.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
        Text(text = skill.description, fontSize = 13.sp, textAlign = TextAlign.Start,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1.8f))
        Divider(color = Color(0xFF83AAF7), modifier = Modifier
            .fillMaxHeight()
            .width(0.5.dp))
    }
}

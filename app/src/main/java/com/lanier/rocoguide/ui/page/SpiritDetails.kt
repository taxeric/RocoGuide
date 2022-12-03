package com.lanier.rocoguide.ui.page

import androidx.annotation.DrawableRes
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.lanier.rocoguide.R
import com.lanier.rocoguide.base.ROUTE_SCREEN_BIG_PIC_LOAD
import com.lanier.rocoguide.base.ROUTE_SCREEN_GENETIC_DETAIL
import com.lanier.rocoguide.entity.Skill
import com.lanier.rocoguide.entity.SpiritEntity
import com.lanier.rocoguide.ui.common.*
import com.lanier.rocoguide.ui.theme.ExtendedTheme
import com.lanier.rocoguide.utils.PreferenceUtil
import com.lanier.rocoguide.utils.logI
import com.lanier.rocoguide.vm.SpiritDetailViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Create by Eric
 * on 2022/7/26
 */
@OptIn(ExperimentalMaterialApi::class)
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
    var showSkillDetail by remember {
        mutableStateOf(Skill())
    }
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
        vm.getSpiritById(spiritId)
    }
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            SkillDetailImpl(skill = showSkillDetail,
                showName = true,
                paddingValues = PaddingValues(10.dp, 10.dp),
                modifier = Modifier
                    .height(480.dp)
                    .background(androidx.compose.material3.MaterialTheme.colorScheme.background))
        },
        backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.background,
        topBar = {
            SmallTopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "",
                        )
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
                            EggImage(
                                id = eggGroupId,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.clip(RoundedCornerShape(50.dp))
                            )
                        }
                    }
                }
            )
        },
        modifier = Modifier.pointerInput(Unit){
            detectTapGestures(onTap = {
                coroutineScope.launch {
                    if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            })
        }
    ) {
        SpiritDetailData(it, navHostController, spirit) {skill ->
            coroutineScope.launch {
                if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                    showSkillDetail = skill
                    bottomSheetScaffoldState.bottomSheetState.expand()
                } else {
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                }
            }
        }
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
fun SpiritDetailData(paddingValues: PaddingValues, navHostController: NavHostController, spirit: SpiritEntity, onClickSkill: (Skill) -> Unit = {}){
    SpiritDetailImpl(paddingValues, spirit, navHostController, onClickSkill)
}

@Composable
fun SpiritDetailImpl(paddingValues: PaddingValues, data: SpiritEntity, navHostController: NavHostController, onClickSkill: (Skill) -> Unit = {}){
    val racialStyle = PreferenceUtil.getRacialValue()
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(paddingValues)
        .verticalScroll(rememberScrollState())) {
        Row {
            SpiritEntityPic(data, modifier = Modifier
                .weight(0.35f)
                .padding(10.dp, 5.dp)) {
                val url = URLEncoder.encode(it, StandardCharsets.UTF_8.toString())
                navHostController.navigate("$ROUTE_SCREEN_BIG_PIC_LOAD/$url")
            }
            SpiritEntityBaseInfo(data, modifier = Modifier
                .weight(0.65f)
                .padding(10.dp, 5.dp))
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = data.description, fontSize = 18.sp, modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp))
        Spacer(modifier = Modifier.height(10.dp))
        when (racialStyle) {
            PreferenceUtil.RACIAL_GRID -> SpiritRacialValueTypeGrid(data)
            PreferenceUtil.RACIAL_PROGRESS -> SpiritRacialValueTypeProgress(data)
            PreferenceUtil.RACIAL_HEXAGONAL -> RacialHexagonal(
                spiritData = data,
                powerTextColor = ExtendedTheme.colors.defaultPowerTvValueColor,
                attackTextColor = ExtendedTheme.colors.defaultAttackTvValueColor,
                defenseTextColor = ExtendedTheme.colors.defaultDefenseTvValueColor,
                magicAttackTextColor = ExtendedTheme.colors.defaultMagicAttackTvValueColor,
                magicDefenseTextColor = ExtendedTheme.colors.defaultMagicDefenseTvValueColor,
                speedTextColor = ExtendedTheme.colors.defaultSpeedTvValueColor,
                baseHexagonalColor = MaterialTheme.colors.secondary,
                realRacialValueLineColor = ExtendedTheme.colors.defaultRacialValueColor,
                modifier = Modifier
                    .height(200.dp)
            )
            else -> SpiritRacialValueTypeGrid(data)
        }
        Spacer(modifier = Modifier.height(10.dp))
        SpiritSkillsV2(data, navHostController, onClickSkill)
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
fun SpiritEntityPic(
    data: SpiritEntity,
    modifier: Modifier,
    toBigView: (String) -> Unit = {}
){
    Box(modifier = modifier
        .clip(RoundedCornerShape(5.dp))
        .background(Color.Yellow)) {
        Column(modifier = Modifier.height(200.dp)) {
            AsyncImage(
                model = data.avatar,
                contentDescription = "avatar",
                modifier = Modifier.height(170.dp)
                    .clickable { toBigView(data.avatar) }
            )
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                AttrImage(attr = data.primaryAttributes, modifier = Modifier
                    .width(20.dp)
                    .height(20.dp))
                data.secondaryAttributes.id?.let {
                    if (it != -1) {
                        Spacer(modifier = Modifier.width(10.dp))
                        AttrImage(
                            attr = data.secondaryAttributes, modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                        )
                    }
                }
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
fun SpiritRacialValueTypeGrid(data: SpiritEntity){
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
fun SpiritRacialValueTypeProgress(data: SpiritEntity){
    Column(modifier = Modifier.fillMaxWidth()) {
        SpiritRacialValueProgressAnim(
            id = R.drawable.ic_race_energy, finalValue = data.racePower)
        SpiritRacialValueProgressAnim(
            id = R.drawable.ic_race_attack, finalValue = data.raceAttack)
        SpiritRacialValueProgressAnim(
            id = R.drawable.ic_race_defense, finalValue = data.raceDefense)
        SpiritRacialValueProgressAnim(
            id = R.drawable.ic_race_magic_attack, finalValue = data.raceMagicAttack)
        SpiritRacialValueProgressAnim(
            id = R.drawable.ic_race_magic_defense, finalValue = data.raceMagicDefense)
        SpiritRacialValueProgressAnim(
            id = R.drawable.ic_race_speed, finalValue = data.raceSpeed)
    }
}

@Composable
fun SpiritRacialValueProgressAnim(
    modifier: Modifier = Modifier,
    @DrawableRes id: Int,
    finalValue: Int
) {
    var progress by remember { mutableStateOf(0f) }
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )
    if (finalValue != 0) {
        val target = finalValue / 255f
        LaunchedEffect(key1 = Unit) {
            delay(1000)
            while (progress < target){
                progress += 0.01f
                delay(10)
            }
        }
    }
    var showValue by remember {
        mutableStateOf(true)
    }
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(10.dp, 2.dp)
        .clickable {
            showValue = !showValue
        },
        verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(3f), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = id), contentDescription = "")
        }
        Column(modifier = Modifier
            .weight(7f)
            .height(20.dp)
            .padding(8.dp, 0.dp)) {
            LinearProgressIndicator(progress = animatedProgress)
            AnimatedVisibility(visible = showValue,
                enter = slideInHorizontally() + fadeIn(),
                exit = slideOutHorizontally() + fadeOut()
            ) {
                Text(
                    text = "$finalValue/255", fontSize = 12.sp,
                    color = ExtendedTheme.colors.defaultRacialValueColor,
                )
            }
        }
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
fun SpiritSkillsV2(data: SpiritEntity, navHostController: NavHostController, onClickSkill: (Skill) -> Unit = {}){
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
            SingleSkillV2(skill, navHostController, onClickSkill)
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
fun SingleSkillV2(skill: Skill, navHostController: NavHostController, onClickSkill: (Skill) -> Unit = {}){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .clickable {
//            navHostController.navigate("$ROUTE_SCREEN_SKILL_DETAIL/${Gson().toJson(skill)}")
            onClickSkill(skill)
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

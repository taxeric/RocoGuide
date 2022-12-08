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
import androidx.constraintlayout.compose.ConstraintLayout
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
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SpiritScreen(navHostController: NavHostController, spiritId: Int){
    val vm = viewModel<SpiritDetailViewModel>()
    val spirit = vm.spiritDetail.collectAsState().value
    val title = spirit.name
    val layEggsEnable = spirit.group.id > 1
    val eggGroup = spirit.group.name
    val eggGroupId = spirit.group.id
    var showEggDialog by remember {
        mutableStateOf(false)
    }
    var showFixDialog by remember {
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
            TopAppBar(
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
                    IconButton(onClick = {
                        showFixDialog = true
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_gulu_base_bg_1),
                            contentDescription = "",
                        )
                    }
                    if (layEggsEnable) {
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
                })
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
    if (showEggDialog) {
        EggDialog(eggGroup, eggGroupId) {
            if (it) {
                navHostController.navigate("${ROUTE_SCREEN_GENETIC_DETAIL}/$eggGroupId/$eggGroup")
            }
            showEggDialog = false
        }
    }
    if (showFixDialog) {
        SpiritDetailsFixDialog {
            showFixDialog = false
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
        .fillMaxSize()
        .padding(paddingValues)
        .verticalScroll(rememberScrollState())
        .background(ExtendedTheme.colors.defaultMainBackground)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .height(210.dp)
        ) {
            SpiritEntityPic(data, modifier = Modifier
                .weight(0.35f)
                .fillMaxHeight()
                .padding(10.dp, 0.dp, 4.dp, 0.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(ExtendedTheme.colors.defaultLazyItemBackground)) {
                val url = URLEncoder.encode(it, StandardCharsets.UTF_8.toString())
                navHostController.navigate("$ROUTE_SCREEN_BIG_PIC_LOAD/$url")
            }
            SpiritEntityBaseInfo(data, modifier = Modifier
                .weight(0.65f)
                .fillMaxHeight()
                .padding(4.dp, 0.dp, 10.dp, 0.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(ExtendedTheme.colors.defaultLazyItemBackground))
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = data.description,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(ExtendedTheme.colors.defaultLazyItemBackground)
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        SpiritRacialView(
            racialStyle = racialStyle,
            data = data,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        SpiritSkillsV2(data, onClickSkill)
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
private fun SpiritRacialView(
    modifier: Modifier = Modifier,
    racialStyle: Int,
    data: SpiritEntity
) {
    Box(
        modifier = modifier
            .padding(10.dp, 0.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(ExtendedTheme.colors.defaultLazyItemBackground)
    ) {
        when (racialStyle) {
            PreferenceUtil.RACIAL_GRID -> {
                SpiritRacialValueTypeGrid(
                    data = data,
                    modifier = Modifier
                )
            }
            PreferenceUtil.RACIAL_PROGRESS -> {
                SpiritRacialValueTypeProgress(
                    data = data,
                    modifier = Modifier
                        .padding(0.dp, 8.dp)
                )
            }
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
                    .padding(0.dp, 8.dp)
            )
            else -> SpiritRacialValueTypeGrid(data = data)
        }
    }
}

@Composable
fun SpiritEntityPic(
    data: SpiritEntity,
    modifier: Modifier,
    toBigView: (String) -> Unit = {}
){
    Column(modifier = modifier) {
        Box(
            modifier = Modifier.height(170.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(painter = painterResource(id = R.drawable.ic_spirit_main_bg), contentDescription = "")
            AsyncImage(
                model = data.avatar,
                contentDescription = "avatar",
                modifier = Modifier
                    .fillMaxHeight()
                    .clickable { toBigView(data.avatar) }
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
        ) {
            AttrImage(
                attr = data.primaryAttributes,
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
            )
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

@Composable
fun SpiritEntityBaseInfo(data: SpiritEntity, modifier: Modifier){
    Column(
        modifier = modifier
            .padding(8.dp, 0.dp)
    ) {
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
fun SpiritRacialValueTypeGrid(
    modifier: Modifier = Modifier,
    data: SpiritEntity
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFF83AAF7), RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SpiritSingleRacialValue(
            modifier = Modifier
                .wrapContentHeight()
                .weight(1f),
            R.drawable.ic_race_energy,
            data.racePower
        )
        SpiritSingleRacialValue(
            modifier = Modifier
                .wrapContentHeight()
                .weight(1f),
            R.drawable.ic_race_attack,
            data.raceAttack
        )
        SpiritSingleRacialValue(
            modifier = Modifier
                .wrapContentHeight()
                .weight(1f),
            R.drawable.ic_race_defense,
            data.raceDefense
        )
        SpiritSingleRacialValue(
            modifier = Modifier
                .wrapContentHeight()
                .weight(1f),
            R.drawable.ic_race_magic_attack,
            data.raceMagicAttack
        )
        SpiritSingleRacialValue(
            modifier = Modifier
                .wrapContentHeight()
                .weight(1f),
            R.drawable.ic_race_magic_defense,
            data.raceMagicDefense
        )
        SpiritSingleRacialValue(
            modifier = Modifier
                .wrapContentHeight()
                .weight(1f),
            R.drawable.ic_race_speed,
            data.raceSpeed
        )
    }
}

@Composable
fun SpiritRacialValueTypeProgress(
    modifier: Modifier = Modifier,
    data: SpiritEntity
){
    Column(modifier = modifier.fillMaxWidth()) {
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
            delay(300)
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
fun SpiritSingleRacialValue(modifier: Modifier = Modifier, racePic: Int, value: Int = 100){
    Column(modifier = modifier
        .fillMaxWidth()) {
        Image(painter = painterResource(id = racePic), contentDescription = "race_value", modifier = Modifier
            .fillMaxWidth()
            .background(ExtendedTheme.colors.defaultRacialGridBgColor)
            .padding(10.dp))
        Text(text = "$value", textAlign = TextAlign.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp))
    }
}

@Composable
fun SpiritSkillsV2(data: SpiritEntity, onClickSkill: (Skill) -> Unit = {}){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    if (data.skills.isEmpty())
                        RoundedCornerShape(8.dp)
                    else
                        RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                )
                .border(1.dp, Color(0xFF83AAF7),
                    if (data.skills.isEmpty())
                        RoundedCornerShape(8.dp)
                    else
                        RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                )
                .background(ExtendedTheme.colors.defaultLazyItemBackground)
                .padding(0.dp, 8.dp)
        ) {
            Text(text = "技能", textAlign = TextAlign.Center, modifier = Modifier.weight(1.2f))
            Text(text = "威力", textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
            Text(text = "PP", textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
            Text(text = "描述", textAlign = TextAlign.Center, modifier = Modifier.weight(1.8f))
        }
        data.skills.forEachIndexed { index, skill ->
            SingleSkillV2(skill, onClickSkill)
        }
        if (data.skills.isNotEmpty()) {
            Divider(
                color = Color(0xFF83AAF7),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )
        }
    }
}

@Composable
fun SingleSkillV2(skill: Skill,onClickSkill: (Skill) -> Unit = {}){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(ExtendedTheme.colors.defaultLazyItemBackground)
            .clickable {
                onClickSkill(skill)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            color = Color(0xFF83AAF7),
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
        )
        Text(text = skill.name, fontSize = 13.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1.2f))
        Text(text = "${skill.value}", fontSize = 13.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
        Text(text = "${skill.amount}", fontSize = 13.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
        Text(text = skill.description, fontSize = 13.sp, textAlign = TextAlign.Start,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1.8f))
        Divider(
            color = Color(0xFF83AAF7),
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
        )
    }
}

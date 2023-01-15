package com.lanier.rocoguide.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.lanier.rocoguide.R
import com.lanier.rocoguide.base.RocoEventModel
import com.lanier.rocoguide.base.cache.LocalCache
import com.lanier.rocoguide.entity.FilterSpiritEntity
import com.lanier.rocoguide.entity.Search
import com.lanier.rocoguide.entity.SeriesEntity
import com.lanier.rocoguide.ui.theme.ExtendedTheme
import com.lanier.rocoguide.ui.theme.LocalDarkTheme
import com.lanier.rocoguide.utils.PreferenceUtil
import com.lanier.rocoguide.vm.spirit.FilterFlow
import com.lanier.rocoguide.vm.spirit.SeriesFlow

/**
 * Create by Eric
 * on 2022/7/30
 */
@Composable
fun SimpleDialog(
    title: String,
    content: String,
    onDismiss: () -> Unit
){
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        )
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(10.dp, 0.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = content,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(10.dp, 0.dp, 10.dp, 10.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(10.dp, 2.dp)) {
                Text(text = stringResource(id = R.string.ok))
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun SpiritFilterDialog(onDismiss: () -> Unit){
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        )
    ) {
        val seriesFlow = SeriesFlow.collectAsState(initial = RocoEventModel.Loading).value
        var curSeries by remember {
            mutableStateOf(SeriesEntity())
        }
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.filter),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            if (seriesFlow == RocoEventModel.Loading) {
                Text(
                    text = stringResource(id = R.string.loading),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            } else {
                val tabs = mutableListOf<CustomTab>().apply {
                    LocalCache.seriesList.forEach {
                        add(CustomTab(it.name))
                    }
                }
                SimpleRadioGroup(
                    tabs = tabs,
                    onSelected = { index, _ ->
                        LocalCache.curSeriesIndex = index
                        curSeries = LocalCache.seriesList[index]
                    },
                    defaultSelected = LocalCache.curSeriesIndex,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 0.dp),
                    contentModifier = Modifier
                        .padding(4.dp, 0.dp)
                ) {  tab, selected, childModifier ->
                    Text(
                        text = tab.text,
                        color = if (selected == tab.tag) ExtendedTheme.colors.defaultRacialGridBgColor else MaterialTheme.colorScheme.onSurface,
                        fontSize = 12.sp,
                        modifier = childModifier
                            .border(
                                1.dp,
                                if (selected == tab.tag) ExtendedTheme.colors.defaultRacialGridBgColor else Color.Transparent,
                                RoundedCornerShape(4.dp)
                            )
                            .padding(2.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            TextButton(
                onClick = {
                    FilterFlow.tryEmit(FilterSpiritEntity(series = curSeries))
                    onDismiss()
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(5.dp, 2.dp)
            ) {
                Text(text = stringResource(id = R.string.sure))
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchDialog(type: Search, label: String = "", onDismiss: (String) -> Unit) {
    Dialog(
        onDismissRequest = { onDismiss("") },
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        )
    ) {
        var mDesc by remember {
            mutableStateOf("")
        }
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.background)
        ) {
            Text(
                text = "搜索${type.title}", modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = mDesc, onValueChange = {
                    mDesc = it
                },
                label = {
                    Text(text = label)
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier.align(Alignment.End)) {
                TextButton(
                    onClick = { onDismiss("") }, modifier = Modifier
                        .padding(5.dp)
                ) {
                    Text(text = stringResource(id = R.string.cancel))
                }
                TextButton(
                    onClick = { onDismiss(mDesc) }, modifier = Modifier
                        .padding(5.dp)
                ) {
                    Text(text = stringResource(id = R.string.sure))
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun DarkThemeDialog(onDismiss: () -> Unit){
    val darkTheme = LocalDarkTheme.current
    var darkValue by remember {
        mutableStateOf(darkTheme.darkThemeValue)
    }
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.background)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            SingleChoiceItem(text = stringResource(id = R.string.follow_system), modifier = Modifier.padding(10.dp, 0.dp),
                selected = darkValue == SettingsHelper.PreferenceDarkTheme.FOLLOW_SYSTEM){
                darkValue = SettingsHelper.PreferenceDarkTheme.FOLLOW_SYSTEM
            }
            SingleChoiceItem(text = stringResource(id = R.string.day), modifier = Modifier.padding(10.dp, 0.dp),
                selected = darkValue == SettingsHelper.PreferenceDarkTheme.OFF){
                darkValue = SettingsHelper.PreferenceDarkTheme.OFF
            }
            SingleChoiceItem(text = stringResource(id = R.string.dark), modifier = Modifier.padding(10.dp, 0.dp),
                selected = darkValue == SettingsHelper.PreferenceDarkTheme.ON){
                darkValue = SettingsHelper.PreferenceDarkTheme.ON
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.align(Alignment.End)) {
                TextButton(onClick = {
                    SettingsHelper.switchDarkThemeMode(darkTheme.darkThemeValue)
                    onDismiss()
                }, modifier = Modifier) {
                    Text(text = stringResource(id = R.string.cancel))
                }
                TextButton(onClick = {
                    onDismiss()
                    SettingsHelper.switchDarkThemeMode(darkValue)
                }, modifier = Modifier) {
                    Text(text = stringResource(id = R.string.sure))
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun RacialValueDialog(onDismiss: () -> Unit) {
    var localStyle by remember {
        mutableStateOf(PreferenceUtil.getRacialValue())
    }
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.background)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            SingleChoiceItem(
                text = stringResource(id = R.string.grid), modifier = Modifier.padding(10.dp, 0.dp),
                selected = localStyle == PreferenceUtil.RACIAL_GRID
            ) {
                localStyle = PreferenceUtil.RACIAL_GRID
            }
            SingleChoiceItem(
                text = stringResource(id = R.string.progress), modifier = Modifier.padding(10.dp, 0.dp),
                selected = localStyle == PreferenceUtil.RACIAL_PROGRESS
            ) {
                localStyle = PreferenceUtil.RACIAL_PROGRESS
            }
            SingleChoiceItem(
                text = stringResource(id = R.string.hexagonal), modifier = Modifier.padding(10.dp, 0.dp),
                selected = localStyle == PreferenceUtil.RACIAL_HEXAGONAL
            ) {
                localStyle = PreferenceUtil.RACIAL_HEXAGONAL
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.align(Alignment.End)) {
                TextButton(onClick = {
                    onDismiss()
                }, modifier = Modifier) {
                    Text(text = stringResource(id = R.string.cancel))
                }
                TextButton(onClick = {
                    PreferenceUtil.updateInt(PreferenceUtil.RACIAL_SHOW_STYLE, localStyle)
                    onDismiss()
                }, modifier = Modifier) {
                    Text(text = stringResource(id = R.string.sure))
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun EggDialog(groupName: String, groupId: Int, onDismiss: (Boolean) -> Unit){
    Dialog(
        onDismissRequest = { onDismiss(false) },
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        )
    ) {
        Column(modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp)) {
            Text(text = buildAnnotatedString {
                append("组别: ")
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(groupName)
                }
                append("\n")
                append(stringResource(id = R.string.lay_egg_tips))
            }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                TextButton(onClick = { onDismiss(false) }, modifier = Modifier) {
                    Text(text = stringResource(id = R.string.cancel))
                }
                TextButton(onClick = { onDismiss(true) }, modifier = Modifier) {
                    Text(text = stringResource(id = R.string.sure))
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun HomepageLuLuDialog(onDismiss: () -> Unit){
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        )
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(10.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = stringResource(id = R.string.waiting_perfection), fontSize = 16.sp, modifier = Modifier
                .fillMaxWidth())
            Spacer(modifier = Modifier.height(20.dp))
            TextButton(onClick = onDismiss, modifier = Modifier
                .align(Alignment.End)
                .padding(5.dp, 2.dp)) {
                Text(text = stringResource(id = R.string.sure))
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun SpiritDetailsFixDialog(onDismiss: () -> Unit){
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        )
    ) {
        Column(modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp)) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = stringResource(id = R.string.fix_data), fontSize = 16.sp, modifier = Modifier
                .fillMaxWidth())
            Spacer(modifier = Modifier.height(20.dp))
            TextButton(onClick = onDismiss, modifier = Modifier
                .align(Alignment.End)
                .padding(5.dp, 2.dp)) {
                Text(text = stringResource(id = R.string.ok))
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun GeneticDialog(onDismiss: (Int) -> Unit){
    Dialog(
        onDismissRequest = { onDismiss(-1) },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Column(modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .background(MaterialTheme.colorScheme.background)) {
            Text(
                text = stringResource(id = R.string.debug),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextButton(onClick = { onDismiss(0) }, modifier = Modifier.fillMaxWidth()){
                Text(text = "三代精灵")
            }
            Spacer(modifier = Modifier.height(20.dp))
            TextButton(onClick = { onDismiss(-1) }, modifier = Modifier
                .align(Alignment.End)
                .padding(5.dp)) {
                Text(text = stringResource(id = R.string.cancel))
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun GeneticMoreDialog(onDismiss: () -> Unit){
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Column(modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .background(MaterialTheme.colorScheme.background)) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "多代遗传", textAlign = TextAlign.Center, fontSize = 19.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = stringResource(id = R.string.genetic_more), fontSize = 13.sp, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(180.dp)
                .verticalScroll(rememberScrollState()))
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onDismiss, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)) {
                Text(text = stringResource(id = R.string.sure))
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Deprecated("无法实现bottomSheetDialog的效果")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SkillDialog(onDismiss: () -> Unit){
    val transitionState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    Dialog(
        onDismissRequest = {
            transitionState.apply {
                targetState = false
            }
            onDismiss()
        },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        AnimatedVisibility(
            visibleState = transitionState,
            enter = slideInVertically(initialOffsetY = {it}),
            exit = slideOutVertically(targetOffsetY = {it})
        ) {
            Column(modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .clip(RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))) {
            }
        }
    }
}

/**
 * 不建议在 [onNegativeEvent] 和 [onPositiveEvent] 方法块内进行dismiss操作
 * 对话框的消失只建议在 [onDismiss] 方法块执行
 */
@Composable
fun ChangeLogDialog(
    log: String,
    url: String,
    mandatory: Boolean,
    size: String,
    isDownloading: Boolean = false,
    onCheckEnable: (Boolean) -> Unit = {},
    onNegativeEvent: (Boolean, Boolean) -> Unit = {_, _ ->},
    onPositiveEvent: (String) -> Unit = {},
    onDismiss: () -> Unit
) {
    var checkEnable by remember {
        mutableStateOf(false)
    }
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = !mandatory,
            dismissOnClickOutside = !mandatory
        )
    ) {
        val inlineContentMap = mapOf(
            "star" to InlineTextContent(
                Placeholder(25.sp, 25.sp, PlaceholderVerticalAlign.Center)
            ){
                Image(painter = painterResource(id = R.drawable.ic_star), contentDescription = null)
            }
        )
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(10.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = R.string.upgrade_version),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.width(10.dp))
                Image(painter = painterResource(id = R.drawable.ic_lulu_normal_1), contentDescription = "",
                    modifier = Modifier.size(36.dp))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = buildAnnotatedString {
                appendInlineContent("star")
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(stringResource(id = R.string.upgrade_content))
                }
            }, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary, inlineContent = inlineContentMap)
            Text(text = buildAnnotatedString {
                withStyle(SpanStyle(fontSize = 13.sp, color = MaterialTheme.colorScheme.onBackground)) {
                    append(log)
                }
                append("\n")
                if (mandatory) {
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.surfaceTint, fontSize = 14.sp)) {
                        append("本版为强制更新\n")
                    }
                }
            }, fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(28.dp, 0.dp)
                    .verticalScroll(rememberScrollState()))
            Text(text = buildAnnotatedString {
                appendInlineContent("star")
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(stringResource(id = R.string.file_size))
                }
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                    append(size)
                }
            }, fontSize = 14.sp, inlineContent = inlineContentMap)
            Spacer(modifier = Modifier.height(10.dp))
            if (!mandatory) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = checkEnable, onCheckedChange = { checkEnable = it })
                    Text(text = stringResource(id = R.string.no_more_tips), fontSize = 13.sp, color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .press {
                                checkEnable = !checkEnable
                            }
                            .padding(0.dp, 2.dp, 2.dp, 2.dp))
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.align(Alignment.End)) {
                TextButton(onClick = {
                    onCheckEnable(checkEnable)
                    onNegativeEvent(mandatory, isDownloading)
                    onDismiss()
                }) {
                    val negativeButton = if (isDownloading)
                        stringResource(id = R.string.close)
                    else {
                        if (mandatory) {
                            stringResource(id = R.string.exit)
                        } else {
                            stringResource(id = R.string.later)
                        }
                    }
                    Text(text = negativeButton)
                }
                Spacer(modifier = Modifier.width(5.dp))
                TextButton(onClick = {
                    onCheckEnable(checkEnable)
                    onPositiveEvent(url)
                    onDismiss()
                }, enabled = !isDownloading) {
                    val downloadStr = if (isDownloading) {
                        stringResource(id = R.string.downloading)
                    } else {
                        stringResource(id = R.string.download_now)
                    }
                    Text(text = downloadStr)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigDialog(
    onDismiss: (Boolean) -> Unit
) {
    var host by remember {
        mutableStateOf("127.0.0.1")
    }
    var port by remember {
        mutableStateOf("8080")
    }
    var alwaysShowTips by remember {
        mutableStateOf(false)
    }
    Dialog(
        onDismissRequest = { onDismiss(false) },
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(12.dp)
        ) {
            Text(
                text = stringResource(id = R.string.dialog_title_set_serve_address),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
                    .fillMaxWidth()
                    .padding(0.dp, 12.dp)
            )
            OutlinedTextField(
                value = host,
                onValueChange = { host = it },
                label = {
                    Text(text = stringResource(id = R.string.host))
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = port,
                onValueChange = { port = it },
                label = {
                    Text(text = stringResource(id = R.string.port))
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Checkbox(checked = alwaysShowTips, onCheckedChange = { alwaysShowTips = it })
                Text(
                    text = stringResource(id = R.string.no_more_tips),
                    modifier = Modifier
                        .clickable {
                            alwaysShowTips = !alwaysShowTips
                        }
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TextButton(onClick = { onDismiss(false) }, modifier = Modifier) {
                    Text(text = stringResource(id = R.string.cancel))
                }
                TextButton(onClick = { onDismiss(true) }, modifier = Modifier) {
                    Text(text = stringResource(id = R.string.sure))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

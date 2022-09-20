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
import com.lanier.rocoguide.entity.Search
import com.lanier.rocoguide.ui.theme.LocalDarkTheme
import com.lanier.rocoguide.utils.PreferenceUtil
import com.lanier.rocoguide.utils.logI

/**
 * Create by Eric
 * on 2022/7/30
 */
@Composable
fun SkillDialog(content: String, onDismiss: () -> Unit){
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        )
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = content, fontSize = 16.sp, modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth())
            Spacer(modifier = Modifier.height(20.dp))
            TextButton(onClick = onDismiss, modifier = Modifier
                .align(Alignment.End)
                .padding(5.dp, 2.dp)) {
                Text(text = "确定")
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

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
                    Text(text = "取消")
                }
                TextButton(
                    onClick = { onDismiss(mDesc) }, modifier = Modifier
                        .padding(5.dp)
                ) {
                    Text(text = "确定")
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
            SingleChoiceItem(text = "跟随系统", modifier = Modifier.padding(10.dp, 0.dp),
                selected = darkValue == SettingsHelper.PreferenceDarkTheme.FOLLOW_SYSTEM){
                darkValue = SettingsHelper.PreferenceDarkTheme.FOLLOW_SYSTEM
            }
            SingleChoiceItem(text = "Day", modifier = Modifier.padding(10.dp, 0.dp),
                selected = darkValue == SettingsHelper.PreferenceDarkTheme.OFF){
                darkValue = SettingsHelper.PreferenceDarkTheme.OFF
            }
            SingleChoiceItem(text = "Dark", modifier = Modifier.padding(10.dp, 0.dp),
                selected = darkValue == SettingsHelper.PreferenceDarkTheme.ON){
                darkValue = SettingsHelper.PreferenceDarkTheme.ON
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.align(Alignment.End)) {
                TextButton(onClick = {
                    SettingsHelper.switchDarkThemeMode(darkTheme.darkThemeValue)
                    onDismiss()
                }, modifier = Modifier) {
                    Text(text = "取消")
                }
                TextButton(onClick = {
                    onDismiss()
                    SettingsHelper.switchDarkThemeMode(darkValue)
                }, modifier = Modifier) {
                    Text(text = "确定")
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
                text = "表格", modifier = Modifier.padding(10.dp, 0.dp),
                selected = localStyle == PreferenceUtil.RACIAL_GRID
            ) {
                localStyle = PreferenceUtil.RACIAL_GRID
            }
            SingleChoiceItem(
                text = "进度", modifier = Modifier.padding(10.dp, 0.dp),
                selected = localStyle == PreferenceUtil.RACIAL_PROGRESS
            ) {
                localStyle = PreferenceUtil.RACIAL_PROGRESS
            }
            SingleChoiceItem(
                text = "六边形", modifier = Modifier.padding(10.dp, 0.dp),
                selected = localStyle == PreferenceUtil.RACIAL_HEXAGONAL
            ) {
                localStyle = PreferenceUtil.RACIAL_HEXAGONAL
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.align(Alignment.End)) {
                TextButton(onClick = {
                    onDismiss()
                }, modifier = Modifier) {
                    Text(text = "取消")
                }
                TextButton(onClick = {
                    PreferenceUtil.updateInt(PreferenceUtil.RACIAL_SHOW_STYLE, localStyle)
                    onDismiss()
                }, modifier = Modifier) {
                    Text(text = "确定")
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
            Row(modifier = Modifier.align(Alignment.End)
            ) {
                TextButton(onClick = { onDismiss(false) }, modifier = Modifier) {
                    Text(text = "取消")
                }
                TextButton(onClick = { onDismiss(true) }, modifier = Modifier) {
                    Text(text = "确定")
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun DataErrorDialog(type: Int, onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        )
    ) {
        Column(modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.background)) {
            val errorStr = when (type) {
                -1 -> "编号对应精灵错误"
                -3 -> "精灵获取失败"
                -4 -> "服务器响应异常"
                else -> "其他异常"
            }
            Text(text = "数据可能有误: $errorStr", modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp))
            Spacer(modifier = Modifier.height(20.dp))
            TextButton(
                onClick = onDismiss, modifier = Modifier
                    .align(Alignment.End)
                    .padding(5.dp)
            ) {
                Text(text = "确定")
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
        Column(modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp)) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = stringResource(id = R.string.waiting_perfection), fontSize = 16.sp, modifier = Modifier
                .fillMaxWidth())
            Spacer(modifier = Modifier.height(20.dp))
            TextButton(onClick = onDismiss, modifier = Modifier
                .align(Alignment.End)
                .padding(5.dp, 2.dp)) {
                Text(text = "确定")
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
            Text(text = "调试", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
            Spacer(modifier = Modifier.height(10.dp))
            TextButton(onClick = { onDismiss(0) }, modifier = Modifier.fillMaxWidth()){
                Text(text = "三代精灵")
            }
            Spacer(modifier = Modifier.height(20.dp))
            TextButton(onClick = { onDismiss(-1) }, modifier = Modifier
                .align(Alignment.End)
                .padding(5.dp)) {
                Text(text = "Cancel")
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
                Text(text = "确定")
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
@OptIn(ExperimentalMaterial3Api::class)
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
                Text(text = "版本更新",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,)
                Spacer(modifier = Modifier.width(10.dp))
                Image(painter = painterResource(id = R.drawable.ic_lulu_normal_1), contentDescription = "",
                    modifier = Modifier.size(36.dp))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = buildAnnotatedString {
                appendInlineContent("star")
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append("更新内容:")
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
                    append("文件大小:")
                }
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                    append(size)
                }
            }, fontSize = 14.sp, inlineContent = inlineContentMap)
            Spacer(modifier = Modifier.height(10.dp))
            if (!mandatory) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = checkEnable, onCheckedChange = { checkEnable = it })
                    Text(text = "本版不再提示", fontSize = 13.sp, color = MaterialTheme.colorScheme.onBackground,
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
                        "关闭"
                    else {
                        if (mandatory) {
                            "退出"
                        } else {
                            "稍后"
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
                        "正在下载"
                    } else {
                        "立即下载"
                    }
                    Text(text = downloadStr)
                }
            }
        }
    }
}

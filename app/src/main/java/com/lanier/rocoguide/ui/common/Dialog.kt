package com.lanier.rocoguide.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.lanier.rocoguide.R
import com.lanier.rocoguide.entity.Search

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
            .background(Color.White)) {
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
    onNegativeEvent: (Boolean) -> Unit = {},
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
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(10.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "版本更新",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "本次更新了以下内容:", fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
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
                .padding(20.dp, 0.dp)
                .verticalScroll(rememberScrollState()))
            Text(text = buildAnnotatedString {
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append("文件大小:")
                }
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                    append(size)
                }
            }, fontSize = 14.sp)
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
                if (!isDownloading) {
                    TextButton(onClick = {
                        onCheckEnable(checkEnable)
                        onNegativeEvent(mandatory)
                        onDismiss()
                    }) {
                        val negativeButton = if (mandatory) {
                            "退出"
                        } else {
                            "稍后"
                        }
                        Text(text = negativeButton)
                    }
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

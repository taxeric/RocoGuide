package com.lanier.rocoguide.ui.common

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.lanier.rocoguide.entity.Search
import com.lanier.rocoguide.entity.UpdateData
import com.lanier.rocoguide.entity.UpdateEntity

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
            Text(text = content, color = Color.Black, fontSize = 16.sp, modifier = Modifier
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
fun SearchDialog(type: Search, onDismiss: (String) -> Unit) {
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
                    Text(text = "精灵名")
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
            .background(MaterialTheme.colorScheme.background)) {
            Text(text = buildAnnotatedString {
                append("组别: ")
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(groupName)
                }
                append("\n")
                append("可遗传技能,是否前往遗传图鉴?")
            }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier
                .align(Alignment.End)
                .padding(5.dp)
                .align(Alignment.End)
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

/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VersionUpdateDialog(
    data: UpdateData,
    onDismiss: () -> Unit = {},
    onCheckEnable: (Boolean) -> Unit,
    onClick: (url: String) -> Unit
) {
    if (data.type == 0 || data.type == 1) {
        var checkEnable by remember {
            mutableStateOf(false)
        }
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = data.mandatory != 1 && data.type != 1,
                dismissOnClickOutside = data.mandatory != 1 && data.type != 1
            )
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.White)
                    .padding(10.dp)
            ) {
                val title = if (data.type == 0) {
                    "公告"
                } else {
                    "版本更新"
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = buildAnnotatedString {
                        append(data.log)
                        append("\n")
                        if (data.type == 1) {
                            if (data.mandatory == 1) {
                                withStyle(SpanStyle(color = Color.Black, fontSize = 16.sp)) {
                                    append("本版为强制更新\n")
                                }
                            }
                            append("文件大小: ${(data.size / 1024 / 1024).toDouble()} MB")
                        }
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .verticalScroll(
                            rememberScrollState()
                        )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Checkbox(checked = checkEnable, onCheckedChange = { checkEnable = it })
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = "本版不再提示")
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(modifier = Modifier.align(Alignment.End)) {
                    TextButton(onClick = {
                        onCheckEnable(checkEnable)
                        onClick(
                            if (data.type == 1) {
                                data.url
                            } else {
                                ""
                            }
                        )
                        onDismiss()
                    }) {
                        val text = if (data.type == 0) {
                            "确定"
                        } else {
                            "立即下载"
                        }
                        Text(text = text)
                    }
                }
            }
        }
    }
}*/

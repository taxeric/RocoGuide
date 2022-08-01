package com.lanier.rocoguide.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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
fun EggDialog(onDismiss: () -> Unit){
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        )
    ) {
        Column(modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .background(Color.White)) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VersionUpdateDialog(
    data: UpdateData,
    onDismiss: () -> Unit = {},
    onCheckEnable: (Boolean) -> Unit,
    onClick: (url: String) -> Unit
) {
    if (data.logType == 0 || data.logType == 1) {
        var checkEnable by remember {
            mutableStateOf(false)
        }
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = !data.mandatory && data.logType != 1,
                dismissOnClickOutside = !data.mandatory && data.logType != 1
            )
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.White)
                    .padding(10.dp)
            ) {
                val title = if (data.logType == 0) {
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
                        if (data.logType == 1) {
                            if (data.mandatory) {
                                withStyle(SpanStyle(color = Color.Black, fontSize = 16.sp)) {
                                    append("本版为强制更新\n")
                                }
                            }
                            append("文件大小: ${data.size}")
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
                            if (data.logType == 1) {
                                data.url
                            } else {
                                ""
                            }
                        )
                        onDismiss()
                    }) {
                        val text = if (data.logType == 0) {
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
}

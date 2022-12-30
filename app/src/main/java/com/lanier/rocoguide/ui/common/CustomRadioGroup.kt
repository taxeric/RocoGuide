package com.lanier.rocoguide.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lanier.rocoguide.utils.logI

/**
 * Create by Eric
 * on 2022/12/18
 */
/**
 * 自定义radiobutton
 *
 * @param modifier 最外层
 * @param contentModifier 子项
 * @param orientation 方向
 * @param tabs 显示内容
 * @param onSelected 选中事件
 * @param content 填充
 */
@Composable
fun SimpleRadioGroup(
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    orientation: Orientation = Orientation.Horizontal,
    defaultSelected: Int = 0,
    tabs: List<CustomTab>,
    onSelected: (Int, CustomTab) -> Unit,
    content: @Composable (
        tab: CustomTab,
        selected: String,
        childModifier: Modifier
    ) -> Unit = { _,_,_ -> },
) {
    if (tabs.isEmpty()) {
        return
    }
    var selectedTab by remember {
        mutableStateOf(tabs[defaultSelected].tag)
    }
    if (orientation == Orientation.Horizontal) {
        Row(
            modifier = modifier
                .selectableGroup()
                .horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ItemView(
                modifier = contentModifier,
                selectedTab = selectedTab,
                tabs = tabs,
                onSelected = { index, tab ->
                    selectedTab = tabs[index].tag
                    onSelected(index, tab)
                },
                content = content
            )
        }
    } else {
        Column(
            modifier = modifier
                .selectableGroup()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ItemView(
                modifier = contentModifier,
                selectedTab = selectedTab,
                tabs = tabs,
                onSelected = { index, tab ->
                    selectedTab = tabs[index].tag
                    onSelected(index, tab)
                },
                content = content
            )
        }
    }
}

@Composable
private fun ItemView(
    modifier: Modifier,
    selectedTab: String,
    tabs: List<CustomTab>,
    onSelected: (Int, CustomTab) -> Unit,
    content: @Composable (
        tab: CustomTab,
        selected: String,
        childModifier: Modifier
    ) -> Unit = { _,_,_ -> },
) {
    tabs.forEachIndexed { index, tab ->
        Box(
            modifier = modifier
                .selectable(
                    selected = selectedTab == tab.tag,
                    onClick = {
                        onSelected(index, tabs[index])
                    },
                    role = Role.RadioButton
                )
        ) {
            content(tab, selectedTab, Modifier.align(Alignment.Center))
        }
    }
}

class CustomTab(
    val text: String,
    val tag: String = text
)

/**
 * 举个例子
 */
@Preview
@Composable
private fun Test() {
    val tabs = mutableListOf<CustomTab>().apply {
        add(CustomTab("1"))
        add(CustomTab("2"))
        add(CustomTab("3"))
        add(CustomTab("4"))
        add(CustomTab("5"))
        add(CustomTab("6"))
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SimpleRadioGroup(
            tabs = tabs,
            onSelected = { index, tab ->
                "current selected -> $index ${tab.text}".logI()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp),
            contentModifier = Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(50.dp))
        ) { tab, selected, childModifier ->
            Text(
                text = tab.text,
                color = if (selected == tab.tag) {
                    Color.White
                } else {
                    Color(155, 155, 155)
                },
                fontSize = 12.sp,
                modifier = childModifier
                    .clip(RoundedCornerShape(50))
                    .background(if (selected == tab.tag) Color.Cyan else Color.White)
                    .padding(4.dp, 2.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        Divider(color = Color.White)
        Spacer(modifier = Modifier.height(20.dp))

        SimpleRadioGroup(
            tabs = tabs,
            orientation = Orientation.Vertical,
            onSelected = { index, tab ->
                "current selected -> $index ${tab.text}".logI()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp),
            contentModifier = Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(50.dp))
        ) { tab, selected, childModifier ->
            Text(
                text = tab.text,
                color = if (selected == tab.tag) {
                    Color.White
                } else {
                    Color(155, 155, 155)
                },
                fontSize = 12.sp,
                modifier = childModifier
                    .clip(RoundedCornerShape(50))
                    .background(if (selected == tab.tag) Color.Cyan else Color.White)
                    .padding(4.dp, 2.dp)
            )
        }
    }
}

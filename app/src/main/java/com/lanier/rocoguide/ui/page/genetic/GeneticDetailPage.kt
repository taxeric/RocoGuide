package com.lanier.rocoguide.ui.page.genetic

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.lanier.rocoguide.R
import com.lanier.rocoguide.base.ROUTE_SCREEN_GENETIC_MORE
import com.lanier.rocoguide.entity.GeneticSpiritData
import com.lanier.rocoguide.entity.SpiritEggGroup
import com.lanier.rocoguide.ui.common.EnableBackBaseScaffoldWithActions
import com.lanier.rocoguide.ui.common.GeneticDialog
import com.lanier.rocoguide.vm.egggroup.GeneticViewModel

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/29 10:22
 * Desc  :
 */
@Composable
fun GeneticDetailScreen(navHostController: NavHostController, id: Int, title: String){
    var showGeneticDialog by remember {
        mutableStateOf(false)
    }
    EnableBackBaseScaffoldWithActions(
        title = title,
        onBack = { navHostController.popBackStack() },
        actions = {
            IconButton(onClick = {
                showGeneticDialog = true
            }) {
                Image(painter = painterResource(id = R.drawable.ic_lay_egg_1), contentDescription = "")
            }
        },
    ) {
        GeneticDetailImpl(navHostController, paddingValues = it, SpiritEggGroup(id, title))
    }
    if (showGeneticDialog) {
        GeneticDialog {
            if (it == 0) {
                navHostController.navigate("$ROUTE_SCREEN_GENETIC_MORE/$id")
            }
            showGeneticDialog = false
        }
    }
}

@Composable
fun GeneticDetailImpl(navHostController: NavHostController, paddingValues: PaddingValues, group: SpiritEggGroup){
    val vm = viewModel<GeneticViewModel>()
    val list = vm.currentGeneticList.collectAsState(initial = emptyList()).value
    LaunchedEffect(key1 = Unit) {
        vm.getGeneticData(group)
    }
    Column(modifier = Modifier.padding(paddingValues)) {
        GeneticDetailList(list)
    }
}

@Composable
fun GeneticDetailList(list: List<GeneticSpiritData>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_male),
            contentDescription = "雄性",
            modifier = Modifier.weight(2.2f)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_female),
            contentDescription = "雌性",
            modifier = Modifier.weight(2.2f)
        )
        Column(modifier = Modifier.weight(3f)) {
            Image(
                painter = painterResource(id = R.drawable.ic_genetic),
                contentDescription = "遗传技能"
            )
        }
    }
    LazyColumn {
        if (list.isEmpty()) {
            item {
                Text(text = "数据还未初始化或出错了", modifier = Modifier.fillMaxWidth())
            }
        } else {
            items(list) {
                GeneticDetailItem(it)
            }
        }
    }
}

@Composable
fun GeneticDetailItem(data: GeneticSpiritData){
    Row(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = data.father.name, textAlign = TextAlign.Center, fontSize = 12.sp, modifier = Modifier.weight(2.2f))
        Text(text = data.mother.name, textAlign = TextAlign.Center, fontSize = 12.sp, modifier = Modifier.weight(2.2f))
        Row(modifier = Modifier
            .weight(3f)
            .horizontalScroll(
                rememberScrollState()
            )){
            data.skills.forEachIndexed {index, it ->
                Text(text = it.name,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(if (index == 0) 0.dp else 5.dp, 2.dp, 0.dp, 2.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(MaterialTheme.colorScheme.inverseOnSurface)
                        .padding(1.dp)
                )
            }
        }
    }
}

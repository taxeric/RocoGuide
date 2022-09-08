package com.lanier.rocoguide.ui.page.genetic

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.lanier.rocoguide.base.ROUTE_SCREEN_GENETIC_DETAIL
import com.lanier.rocoguide.entity.SpiritEggGroup
import com.lanier.rocoguide.ui.common.*
import com.lanier.rocoguide.vm.EggGroupViewModel

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/29 10:22
 * Desc  :
 */
@Composable
fun GeneticScreen(navHostController: NavHostController, title: String){
    EnableBackBaseScaffold(
        title = title,
        onBack = { navHostController.popBackStack() },
    ) {
        GeneticGroupImpl(navHostController, paddingValues = it)
    }
}

@Composable
fun GeneticGroupImpl(navHostController: NavHostController, paddingValues: PaddingValues){
    val vm = viewModel<EggGroupViewModel>()
    val list = vm.eggGroupFlow.collectAsLazyPagingItems()
    Column(modifier = Modifier.padding(paddingValues)) {
        GeneticGroupList(navHostController, list)
    }
}

@Composable
fun GeneticGroupList(navHostController: NavHostController, list: LazyPagingItems<SpiritEggGroup>){
    RefreshLazyVerticalGrid(data = list, columns = 2,
        contentPadding = PaddingValues(10.dp, 5.dp)) { index, data ->
        GeneticEggGroupItem(index, data, navHostController)
    }
}

@Composable
fun GeneticEggGroupItem(index: Int, data: SpiritEggGroup, navHostController: NavHostController){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .clip(
            if (index % 2 == 0)
                RoundedCornerShape(24.dp, 8.dp, 8.dp, 24.dp)
            else
                RoundedCornerShape(8.dp, 24.dp, 24.dp, 8.dp)
        )
        .background(Color(data.randomBackgroundColor))
        .clickable {
            if (data.id != 1) {
                navHostController.navigate("$ROUTE_SCREEN_GENETIC_DETAIL/${data.id}/${data.name}")
            }
        }
        .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(0.3f), horizontalAlignment = Alignment.CenterHorizontally) {
            EggGroupImage(eg = data, contentScale = ContentScale.Crop, modifier = Modifier.size(24.dp))
        }
        Text(text = data.name, modifier = Modifier.weight(0.5f))
        Text(text = "#${index + 1}", fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.2f))
    }
}

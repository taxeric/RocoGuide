package com.lanier.rocoguide.ui.page

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.lanier.rocoguide.entity.LineageEntity
import com.lanier.rocoguide.ui.common.EnableBackBaseScaffold
import com.lanier.rocoguide.ui.common.RefreshLazyColumn
import com.lanier.rocoguide.ui.theme.ExtendedTheme
import com.lanier.rocoguide.vm.LineageVM

/**
 * Create by Eric
 * on 2022/12/15
 */
@Composable
fun LineageScreen(navHostController: NavHostController, title: String) {
    EnableBackBaseScaffold(
        title = title,
        onBack = { navHostController.popBackStack() },
    ) {
        LineageScreenImpl(it)
    }
}

@Composable
fun LineageScreenImpl(paddingValues: PaddingValues) {
    val vm = viewModel<LineageVM>()
    val list = vm.pager.collectAsLazyPagingItems()
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .background(ExtendedTheme.colors.defaultMainBackground)
    ) {
        LineageRv(list)
    }
}

@Composable
fun LineageRv(list: LazyPagingItems<LineageEntity>) {
    RefreshLazyColumn(
        data = list,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = PaddingValues(8.dp)
    ) { _, item ->
        LineageItem(item)
    }
}

@Composable
fun LineageItem(item: LineageEntity) {
    var showIntroduce by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 4.dp, 0.dp, 0.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(ExtendedTheme.colors.defaultLazyItemBackground)
            .padding(8.dp)
            .animateContentSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "#${item.id}",
                color = MaterialTheme.colorScheme.outline,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
            )
            AsyncImage(
                model = item.icon,
                contentDescription = "icon",
                modifier = Modifier
                    .size(36.dp)
                    .weight(1f)
            )
            Text(
                text = item.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .weight(2f)
            )
            IconButton(
                onClick = { showIntroduce = !showIntroduce },
                modifier = Modifier
                    .weight(1f)
            ) {
                Icon(
                    imageVector = if (showIntroduce) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = "",
                )
            }
        }
        if (showIntroduce) {
            Text(
                text = item.introduce,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }
}

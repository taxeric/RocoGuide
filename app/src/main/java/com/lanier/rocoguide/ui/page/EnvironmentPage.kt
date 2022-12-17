package com.lanier.rocoguide.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.lanier.rocoguide.entity.EnvironmentEntity
import com.lanier.rocoguide.ui.common.EnableBackBaseScaffold
import com.lanier.rocoguide.ui.common.RefreshLazyColumn
import com.lanier.rocoguide.ui.theme.ExtendedTheme
import com.lanier.rocoguide.vm.EnvironmentVM

/**
 * Create by Eric
 * on 2022/12/15
 */
@Composable
fun EnvironmentScreen(navHostController: NavHostController, title: String) {
    EnableBackBaseScaffold(
        title = title,
        onBack = { navHostController.popBackStack() },
    ) {
        EnvironmentScreenImpl(paddingValues = it)
    }
}

@Composable
fun EnvironmentScreenImpl(paddingValues: PaddingValues) {
    val vm = viewModel<EnvironmentVM>()
    val list = vm.pager.collectAsLazyPagingItems()
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .background(ExtendedTheme.colors.defaultMainBackground)
    ) {
        EnvironmentRv(list = list)
    }
}

@Composable
fun EnvironmentRv(list: LazyPagingItems<EnvironmentEntity>) {
    RefreshLazyColumn(
        data = list,
        contentPadding = PaddingValues(8.dp)
    ) { _, item ->
        EnvironmentItem(item)
    }
}

@Composable
fun EnvironmentItem(item: EnvironmentEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 4.dp, 0.dp, 0.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(ExtendedTheme.colors.defaultLazyItemBackground)
            .clickable { }
            .padding(8.dp),
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
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier
                .weight(2f)
        )
    }
}

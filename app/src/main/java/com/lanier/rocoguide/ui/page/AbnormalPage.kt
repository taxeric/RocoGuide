package com.lanier.rocoguide.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import com.lanier.rocoguide.entity.AbnormalEntity
import com.lanier.rocoguide.ui.common.EnableBackBaseScaffold
import com.lanier.rocoguide.ui.common.RefreshLazyColumn
import com.lanier.rocoguide.ui.common.SimpleDialog
import com.lanier.rocoguide.ui.theme.ExtendedTheme
import com.lanier.rocoguide.vm.AbnormalVM

/**
 * Create by Eric
 * on 2022/12/15
 */
@Composable
fun AbnormalScreen(navHostController: NavHostController, title: String) {
    EnableBackBaseScaffold(
        title = title,
        onBack = { navHostController.popBackStack() },
    ) {
        AbnormalScreenImpl(it)
    }
}

@Composable
fun AbnormalScreenImpl(paddingValues: PaddingValues) {
    val vm = viewModel<AbnormalVM>()
    val list = vm.pager.collectAsLazyPagingItems()
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .background(ExtendedTheme.colors.defaultMainBackground)
    ) {
        AbnormalRv(list = list)
    }
}

@Composable
fun AbnormalRv(list: LazyPagingItems<AbnormalEntity>) {
    RefreshLazyColumn(
        data = list,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = PaddingValues(8.dp)
    ) { _, item ->
        AbnormalItem(item)
    }
}

@Composable
fun AbnormalItem(item: AbnormalEntity) {
    var showDialog by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 4.dp, 0.dp, 0.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(ExtendedTheme.colors.defaultLazyItemBackground)
            .clickable { showDialog = !showDialog }
            .padding(12.dp),
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
        Text(
            text = item.name,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier
                .weight(2f)
        )
    }
    if (showDialog) {
        SimpleDialog(title = item.name, content = item.introduce) {
            showDialog = !showDialog
        }
    }
}

package com.lanier.rocoguide.ui.page.lab

import android.app.Activity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.lanier.rocoguide.base.cache.LocalCache
import com.lanier.rocoguide.entity.DynamicIconEntity
import com.lanier.rocoguide.ui.common.CommonBaseScaffold
import com.lanier.rocoguide.ui.common.baseBackground
import com.lanier.rocoguide.ui.theme.ExtendedTheme
import com.lanier.rocoguide.utils.DynamicIconUtil
import com.lanier.rocoguide.vm.lab.LabVM

/**
 * Create by Eric
 * on 2023/1/25
 */
@Composable
fun DynamicIconScreen(
    navHostController: NavHostController,
    title: String
) {
    CommonBaseScaffold(
        title = title,
        showNavigationIcon = true,
        onBack = {
            navHostController.popBackStack()
        },
    ) {
        DynamicIconScreenImpl(paddingValues = it)
    }
}

@Composable
fun DynamicIconScreenImpl(paddingValues: PaddingValues) {
    val vm = viewModel<LabVM>()
    DisposableEffect(key1 = vm) {
        vm.readIconConfig()
        onDispose {}
    }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .baseBackground()
    ) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            contentPadding = PaddingValues(10.dp, 6.dp)
        ){
            itemsIndexed(LocalCache.dynamicIcons) { index, item ->
                DynamicIconItem(item) {
                    vm.writeIconConfig(index)
                    DynamicIconUtil.replaceIcon(
                        context.getString(item.launchName),
                        (context as Activity).componentName,
                        context
                    )
                }
            }
        }
    }
}

@Composable
fun DynamicIconItem(
    item: DynamicIconEntity,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(144.dp)
            .clip(RoundedCornerShape(10))
            .background(ExtendedTheme.colors.defaultLazyItemBackground)
            .border(
                width = 1.dp,
                color = if (item.selected) MaterialTheme.colorScheme.primary
                else ExtendedTheme.colors.defaultLazyItemBackground,
                shape = RoundedCornerShape(10)
            )
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = item.resId),
            contentDescription = "",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

package com.lanier.rocoguide.ui.page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

/**
 * Create by Eric
 * on 2022/7/25
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpiritScreen(navHostController: NavHostController, title: String){
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            var startSearch by remember {
                mutableStateOf(false)
            }
            var searchStr by remember {
                mutableStateOf("")
            }
            Column {
                SmallTopAppBar(
                    title = { Text(text = title) },
                    actions = {
                        IconButton(onClick = {
                            startSearch = !startSearch
                        }) {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = "search")
                        }
                    }
                )
                AnimatedVisibility(visible = startSearch) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = searchStr,
                            onValueChange = {
                                searchStr = it
                            },
                            label = {
                                Text(text = "搜索")
                            },
                            trailingIcon = {
                                IconButton(onClick = {
                                    searchStr = ""
                                }) {
                                    Icon(imageVector = Icons.Filled.Clear, contentDescription = "clear")
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp))
                    }
                }
            }
        }
    ) { innerPadding ->
        SpiritMainList(navHostController, innerPadding)
    }
}

@Composable
fun SpiritMainList(navHostController: NavHostController, paddingValues: PaddingValues){
    Column(modifier = Modifier.padding(paddingValues)) {
    }
}

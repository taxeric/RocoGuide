package com.lanier.rocoguide.ui.page.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lanier.rocoguide.entity.Screen
import com.lanier.rocoguide.ui.page.NewsScreen
import com.lanier.rocoguide.ui.page.OtherScreen
import com.lanier.rocoguide.ui.page.SpiritScreen

/**
 * Create by Eric
 * on 2022/7/25
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainHome(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            var selectIndex by remember {
                mutableStateOf(0)
            }
            val items = listOf(
                Screen.NewsList,
                Screen.SpiritList,
                Screen.OtherList
            )
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectIndex == index,
                        onClick = { selectIndex = index },
                        icon = {
                            Icon(imageVector = Icons.Filled.Home, contentDescription = "home")
                        },
                        label = {
                            Text(text = item.title)
                        }
                    )
                }
            }
        }
    ) {
        NavBar(navController = navController, padding = it)
    }
}

@Composable
fun NavBar(navController: NavHostController, padding: PaddingValues){
    NavHost(
        navController = navController,
        startDestination = Screen.NewsList.route,
        modifier = Modifier.padding(padding)
    ){
        composable(Screen.NewsList.route) {
            NewsScreen(navController, title = Screen.NewsList.title)
        }
        composable(Screen.SpiritList.route) {
            SpiritScreen(navController, title = Screen.SpiritList.title)
        }
        composable(Screen.OtherList.route) {
            OtherScreen(navController, title = Screen.OtherList.title)
        }
    }
}

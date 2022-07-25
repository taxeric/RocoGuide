package com.lanier.rocoguide.ui.page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lanier.plugin_base.logE
import com.lanier.rocoguide.base.ROUTE_PARAMS_WEB_VIEW_TITLE
import com.lanier.rocoguide.base.ROUTE_PARAMS_WEB_VIEW_URL
import com.lanier.rocoguide.entity.Screen

/**
 * Create by Eric
 * on 2022/7/25
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainHome(){
    val bottomState = rememberSaveable {
        mutableStateOf(true)
    }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    when (navBackStackEntry?.destination?.route){
        Screen.NewsList.route,
        Screen.SpiritList.route,
        Screen.OtherList.route -> {
            bottomState.value = true
        }
        else -> {
            bottomState.value = false
        }
    }
    Scaffold(
        bottomBar = {
            BottomBar(bottomState = bottomState, navController)
        }
    ) {
        NavBar(navController = navController, padding = it)
    }
}

@Composable
fun BottomBar(bottomState: MutableState<Boolean>, navController: NavHostController){
    var selectIndex by remember {
        mutableStateOf(0)
    }
    val items = listOf(
        Screen.NewsList,
        Screen.SpiritList,
        Screen.OtherList
    )
    AnimatedVisibility(
        visible = bottomState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        NavigationBar {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectIndex == index,
                    onClick = {
                        selectIndex = index
                        navController.navigate(item.route){
                            popUpTo(navController.graph.findStartDestination().id){
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
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
        composable(
            route = "${Screen.WebViewPage.route}/{${ROUTE_PARAMS_WEB_VIEW_TITLE}}/{${ROUTE_PARAMS_WEB_VIEW_URL}}",
            arguments = listOf(
                navArgument(ROUTE_PARAMS_WEB_VIEW_TITLE) {
                    type = NavType.StringType
                },
                navArgument(ROUTE_PARAMS_WEB_VIEW_URL) {
                    type = NavType.StringType
                }
            )
        ) {
            val argument = requireNotNull(it.arguments)
            val title = argument.getString(ROUTE_PARAMS_WEB_VIEW_TITLE)?: ""
            val url = argument.getString(ROUTE_PARAMS_WEB_VIEW_URL)?: ""
            WebViewByUrl(navController, title, url)
        }
    }
}

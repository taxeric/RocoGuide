package com.lanier.rocoguide.ui.page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.lanier.rocoguide.base.*
import com.lanier.rocoguide.entity.Screen
import com.lanier.rocoguide.entity.Skill
import com.lanier.rocoguide.ui.common.CommonBigPicView
import com.lanier.rocoguide.ui.common.WelcomeDialog
import com.lanier.rocoguide.ui.page.genetic.GeneticDetailScreen
import com.lanier.rocoguide.ui.page.genetic.GeneticResultScreen
import com.lanier.rocoguide.ui.page.genetic.GeneticScreen
import com.lanier.rocoguide.ui.page.lab.DynamicIconScreen
import com.lanier.rocoguide.ui.page.miscellaneous.*
import com.lanier.rocoguide.ui.page.news.NewsScreen
import com.lanier.rocoguide.ui.page.search.SearchBaseScreen
import com.lanier.rocoguide.ui.page.settings.SettingsScreen
import com.lanier.rocoguide.ui.page.spirit.SkillDetailScreen
import com.lanier.rocoguide.ui.page.spirit.SkillScreen
import com.lanier.rocoguide.ui.page.spirit.SpiritScreen

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
    val currentDestination = navBackStackEntry?.destination
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
            BottomBar(bottomState = bottomState, currentDestination, navController)
        }
    ) {
        NavBar(navController = navController, padding = it)
    }
}

@Composable
fun BottomBar(bottomState: MutableState<Boolean>, navDestination: NavDestination?, navController: NavHostController){
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
                    selected = navDestination?.hierarchy?.any { it.route == item.route } == true,
                    onClick = {
                        navController.navigate(item.route){
                            popUpTo(navController.graph.findStartDestination().id){
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        if (item.resId != -1) {
                            Image(
                                painter = painterResource(id = item.resId),
                                contentDescription = "",
                                modifier = Modifier.size(24.dp))
                        } else {
                            Icon(imageVector = Icons.Filled.Home, contentDescription = "home")
                        }
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
            com.lanier.rocoguide.ui.page.spirit.SpiritScreen(
                navController,
                title = Screen.SpiritList.title
            )
        }
        composable(Screen.OtherList.route) {
            OtherScreen(navController, title = Screen.OtherList.title)
        }
        composable(Screen.SkillList.route) {
            SkillScreen(navController, title = Screen.SkillList.title)
        }
        composable(Screen.Personality.route) {
            PersonalityScreen(navController, title = Screen.Personality.title)
        }
        composable(Screen.GeneticList.route) {
            GeneticScreen(navController, title = Screen.GeneticList.title)
        }
        composable(Screen.Environment.route) {
            EnvironmentScreen(navController, title = Screen.Environment.title)
        }
        composable(Screen.Abnormal.route) {
            AbnormalScreen(navController, title = Screen.Abnormal.title)
        }
        composable(Screen.Lineage.route) {
            LineageScreen(navController, title = Screen.Lineage.title)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController, title = Screen.Settings.title)
        }
        composable(Screen.Thank.route) {
            ThankScreen(navController, title = Screen.Thank.title)
        }
        composable(Screen.BGM.route) {
            BGMScreen(navController, title = Screen.BGM.title)
        }
        composable(Screen.LabPage.route) {
            LabScreen(navController, title = Screen.LabPage.title)
        }
        composable(Screen.LabDynamicIcon.route) {
            DynamicIconScreen(navController, title = Screen.LabDynamicIcon.title)
        }
        composable(
            route = "${Screen.GeneticDetail.route}/{${ROUTE_PARAMS_GROUP_ID}}/{${ROUTE_PARAMS_GROUP_TITLE}}",
            arguments = listOf(
                navArgument(ROUTE_PARAMS_GROUP_ID) {
                    type = NavType.IntType
                },
                navArgument(ROUTE_PARAMS_GROUP_TITLE) {
                    type = NavType.StringType
                }
            )
        ) {
            val argument = requireNotNull(it.arguments)
            val id = argument.getInt(ROUTE_PARAMS_GROUP_ID)
            val title = argument.getString(ROUTE_PARAMS_GROUP_TITLE)?:"出错了"
            GeneticDetailScreen(navController, title = title, id = id)
        }
        composable(
            route = "${Screen.GeneticMore.route}/{${ROUTE_PARAMS_GROUP_ID}}",
            arguments = listOf(
                navArgument(ROUTE_PARAMS_GROUP_ID) {
                    type = NavType.IntType
                }
            )
        ) {
            val argument = requireNotNull(it.arguments)
            val id = argument.getInt(ROUTE_PARAMS_GROUP_ID)
            GeneticResultScreen(navController, title = Screen.GeneticMore.title, groupId = id)
        }
        composable(
            route = "${Screen.SpiritDetail.route}/{${ROUTE_PARAMS_SPIRIT_ID}}",
            arguments = listOf(
                navArgument(ROUTE_PARAMS_SPIRIT_ID) {
                    type = NavType.IntType
                }
            )
        ) {
            val argument = requireNotNull(it.arguments)
            val id = argument.getInt(ROUTE_PARAMS_SPIRIT_ID)
            SpiritScreen(navController, id)
        }
        composable(
            route = "${Screen.BigPicView.route}/{${ROUTE_PARAMS_PIC_URL}}",
            arguments = listOf(
                navArgument(ROUTE_PARAMS_PIC_URL) {
                    type = NavType.StringType
                }
            )
        ) {
            val url = requireNotNull(it.arguments).getString(ROUTE_PARAMS_PIC_URL)
            CommonBigPicView(navController, url)
        }
        composable(
            route = "${Screen.SkillDetail.route}/{${ROUTE_PARAMS_SKILL}}",
            arguments = listOf(
                navArgument(ROUTE_PARAMS_SKILL) {
                    type = NavType.StringType
                }
            )
        ){
            val argument = requireNotNull(it.arguments)
            val skill = argument.getString(ROUTE_PARAMS_SKILL)
            val mSkill = if (skill.isNullOrEmpty()) {
                Skill(name = "出错了")
            } else {
                Gson().fromJson(skill, Skill::class.java)
            }
            SkillDetailScreen(navController, mSkill)
        }
        composable(
            route = "${Screen.SearchList.route}/{${ROUTE_PARAMS_KEYWORDS}}/{${ROUTE_PARAMS_SEARCH_TYPE}}",
            arguments = listOf(
                navArgument(ROUTE_PARAMS_KEYWORDS) {
                    type = NavType.StringType
                },
                navArgument(ROUTE_PARAMS_SEARCH_TYPE) {
                    type = NavType.IntType
                }
            )
        ) {
            val argument = requireNotNull(it.arguments)
            val keywords = argument.getString(ROUTE_PARAMS_KEYWORDS)?: ""
            val type = argument.getInt(ROUTE_PARAMS_SEARCH_TYPE)
            SearchBaseScreen(navController, keywords, type)
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
    WelcomeDialog {
        navController.navigate(ROUTE_SCREEN_MAIN_SETTINGS)
    }
}

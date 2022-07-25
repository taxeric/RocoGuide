package com.lanier.rocoguide.entity

import com.lanier.rocoguide.base.ROUTE_SCREEN_MAIN_NEWS_LIST
import com.lanier.rocoguide.base.ROUTE_SCREEN_MAIN_OTHER_LIST
import com.lanier.rocoguide.base.ROUTE_SCREEN_MAIN_SPIRIT_LIST

/**
 * Create by Eric
 * on 2022/7/25
 */
sealed class Screen (val route: String, val title: String) {
    object NewsList : Screen(ROUTE_SCREEN_MAIN_NEWS_LIST, "新闻")
    object SpiritList : Screen(ROUTE_SCREEN_MAIN_SPIRIT_LIST, "精灵")
    object OtherList: Screen(ROUTE_SCREEN_MAIN_OTHER_LIST, "其他")
}
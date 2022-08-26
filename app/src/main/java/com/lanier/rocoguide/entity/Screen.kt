package com.lanier.rocoguide.entity

import androidx.annotation.DrawableRes
import com.lanier.rocoguide.R
import com.lanier.rocoguide.base.*

/**
 * Create by Eric
 * on 2022/7/25
 */
sealed class Screen (val route: String, val title: String, @DrawableRes val resId: Int = -1) {
    object NewsList : Screen(ROUTE_SCREEN_MAIN_NEWS_LIST, "新闻")
    object SpiritList : Screen(ROUTE_SCREEN_MAIN_SPIRIT_LIST, "精灵", R.drawable.ic_altas)
    object OtherList: Screen(ROUTE_SCREEN_MAIN_OTHER_LIST, "其他")
    object SpiritDetail: Screen(ROUTE_SCREEN_SPIRIT_DETAIL, "精灵详情")
    object SearchList: Screen(ROUTE_SCREEN_SEARCH_LIST, "搜索")
    object SkillList: Screen(ROUTE_SCREEN_MAIN_SKILL_LIST, "技能")
    object SkillDetail: Screen(ROUTE_SCREEN_SKILL_DETAIL, "技能详情")
    object Personality: Screen(ROUTE_SCREEN_MAIN_PERSONALITY, "性格修正")
    object WebViewPage: Screen(ROUTE_SCREEN_WEB_VIEW, "")
}

sealed class Search (val type: Int, val title: String) {
    object Spirit: Search(0, "精灵")
    object Skill: Search(1, "技能")
    object News: Search(2, "新闻")
}

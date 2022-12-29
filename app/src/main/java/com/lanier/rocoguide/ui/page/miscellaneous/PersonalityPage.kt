package com.lanier.rocoguide.ui.page.miscellaneous

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.lanier.rocoguide.base.cache.LocalCache
import com.lanier.rocoguide.entity.PersonalityEntity
import com.lanier.rocoguide.ui.common.EnableBackBaseScaffold
import com.lanier.rocoguide.ui.common.SingleTitle

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/25 16:32
 * Desc  :
 */
@Composable
fun PersonalityScreen(navHostController: NavHostController, title: String){
    EnableBackBaseScaffold(title = title, onBack = { navHostController.popBackStack() }) {
        PersonalityScreenImpl(paddingValues = it)
    }
}

@Composable
fun PersonalityScreenImpl(paddingValues: PaddingValues){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(paddingValues)
        .verticalScroll(rememberScrollState())) {
        SingleClassPersonality(title = "平衡类", personalities = LocalCache.balancePersonalities, modifier = Modifier
            .padding(10.dp))
        Spacer(modifier = Modifier.height(10.dp))
        SingleClassPersonality(title = "攻击类", personalities = LocalCache.attackPersonalities, modifier = Modifier
            .padding(10.dp))
        Spacer(modifier = Modifier.height(10.dp))
        SingleClassPersonality(title = "防御类", personalities = LocalCache.defensePersonalities, modifier = Modifier
            .padding(10.dp))
        Spacer(modifier = Modifier.height(10.dp))
        SingleClassPersonality(title = "魔攻类", personalities = LocalCache.magicAttackPersonalities, modifier = Modifier
            .padding(10.dp))
        Spacer(modifier = Modifier.height(10.dp))
        SingleClassPersonality(title = "魔抗类", personalities = LocalCache.magicDefensePersonalities, modifier = Modifier
            .padding(10.dp))
        Spacer(modifier = Modifier.height(10.dp))
        SingleClassPersonality(title = "速度类", personalities = LocalCache.speedPersonalities, modifier = Modifier
            .padding(10.dp))
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun SingleClassPersonality(modifier: Modifier = Modifier, title: String, personalities: MutableList<PersonalityEntity>){
    Column(modifier = modifier.fillMaxWidth()) {
        SingleTitle(title = title)
        personalities.forEach {
            SinglePersonality(item = it)
        }
    }
}

@Composable
fun SinglePersonality(item: PersonalityEntity){
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = item.name, modifier = Modifier.weight(1f))
        Text(text = item.raise, modifier = Modifier.weight(1f))
        Text(text = item.down, modifier = Modifier.weight(1f))
    }
}

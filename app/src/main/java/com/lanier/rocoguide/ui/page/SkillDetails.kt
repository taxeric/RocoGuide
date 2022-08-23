package com.lanier.rocoguide.ui.page

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.lanier.plugin_base.logI
import com.lanier.rocoguide.entity.Skill

/**
 * Create by Eric
 * on 2022/8/20
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkillDetailScreen(navHostController: NavHostController, skill: Skill? = Skill(name = "出错了")){
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            SmallTopAppBar(
                title = { Text(text = skill!!.name) },
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "",
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        SkillDetailImpl(innerPadding, skill)
    }
}

@Composable
fun SkillDetailImpl(paddingValues: PaddingValues, skill: Skill?) {
    Column(modifier = Modifier.padding(paddingValues)) {
        if (skill == null) {
            Text(text = "出错了")
        } else {
            Text(text = skill.name, fontSize = 18.sp)
            Row {
                Text(text = skill.attributes.name!!)
                Text(text = skill.skillType.name)
            }
            Text(text = skill.description)
            Row {
                Text(text = "威力: ")
                Text(text = skill.value.toString())
            }
            Row {
                Text(text = "PP: ")
                Text(text = skill.amount.toString())
            }
            Row {
                Text(text = "是否必中: ")
                Text(text = if (skill.isBe) "是" else "否")
            }
            Row {
                Text(text = "能否遗传: ")
                Text(text = if (skill.isGenetic) "是" else "否")
            }
        }
        "receive -> $skill".logI()
    }
}

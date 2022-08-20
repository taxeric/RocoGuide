package com.lanier.rocoguide.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        "receive -> $skill".logI()
    }
}

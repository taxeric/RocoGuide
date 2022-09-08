package com.lanier.rocoguide.ui.page.genetic

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.lanier.rocoguide.R
import com.lanier.rocoguide.base.cache.LocalCache
import com.lanier.rocoguide.entity.GeneticSpiritData
import com.lanier.rocoguide.ui.common.EnableBackBaseScaffoldWithActions
import com.lanier.rocoguide.ui.common.GeneticMoreDialog
import com.lanier.rocoguide.utils.logI

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/29 10:22
 * Desc  :
 */
@Composable
fun GeneticResultScreen(navHostController: NavHostController, title: String, groupId: Int){
    var showGeneticDialog by remember {
        mutableStateOf(false)
    }
    EnableBackBaseScaffoldWithActions(
        title = title,
        onBack = { navHostController.popBackStack() },
        actions = {
            IconButton(onClick = { showGeneticDialog = true }) {
                Image(painter = painterResource(id = R.drawable.ic_lay_egg), contentDescription = "",
                    modifier = Modifier.size(24.dp))
            }
        }
    ) {
        GeneticResultImpl(navHostController, paddingValues = it, groupId.toString())
    }
    if (showGeneticDialog) {
        GeneticMoreDialog {
            showGeneticDialog = false
        }
    }
}

@Composable
fun GeneticResultImpl(navHostController: NavHostController, paddingValues: PaddingValues, groupId: String){
    var fatherName by remember {
        mutableStateOf("")
    }
    var motherName by remember {
        mutableStateOf("")
    }
    var getData by remember {
        mutableStateOf(false)
    }
    val list = remember {
        mutableStateListOf<GeneticSpiritData>()
    }
    var errorMsg by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize()) {
        OutlinedTextField(
            value = fatherName,
            onValueChange = {
                fatherName = it
            },
            label = {
                Text(text = "雄性精灵名字")
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 2.dp)
        )
        OutlinedTextField(
            value = motherName,
            onValueChange = {
                motherName = it
            },
            label = {
                Text(text = "雌性精灵名字")
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 2.dp)
        )
        Button(
            onClick = {
                getData = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 2.dp)
        ) {
            Text(text = "获取")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = errorMsg, color = MaterialTheme.colorScheme.error)
        GeneticDetailList(list)
    }
    if (getData) {
        if (fatherName.isEmpty() || motherName.isEmpty()) {
            errorMsg = "需完善父母名字"
        } else {
            val result = LocalCache.calculateSkills(fatherName, motherName, groupId)
            if (result.isSuccess) {
                val baseData = result.getOrDefault(LocalCache.CalculateEntity("unknow error"))
                if (baseData.data.isEmpty()) {
                    errorMsg = baseData.errorMsg
                } else {
                    baseData.data.forEach {
                        "${it.father.name} ${it.mother.name} = ${it.skills}".logI()
                    }
                    list.clear()
                    list.addAll(baseData.data)
                    errorMsg = ""
                }
            }
        }
        getData = false
    }
}

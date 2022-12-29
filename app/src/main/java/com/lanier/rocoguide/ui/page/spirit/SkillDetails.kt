package com.lanier.rocoguide.ui.page.spirit

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.lanier.rocoguide.entity.Skill
import com.lanier.rocoguide.ui.common.AttrImage
import com.lanier.rocoguide.ui.common.EnableBackBaseScaffold
import com.lanier.rocoguide.utils.logI

/**
 * Create by Eric
 * on 2022/8/20
 */
@Composable
fun SkillDetailScreen(navHostController: NavHostController, skill: Skill? = Skill(name = "出错了")){
    EnableBackBaseScaffold(title = skill!!.name, onBack = { navHostController.popBackStack() }) {
        SkillDetailImpl(paddingValues = it, skill = skill)
    }
}

@Composable
fun SkillDetailImpl(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    skill: Skill?,
    showName: Boolean = false
) {
    Column(modifier = modifier.padding(paddingValues)) {
        if (skill == null) {
            Text(text = "出错了")
        } else {
            if (showName) {
                Text(text = skill.name, fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp, 0.dp))
                Spacer(modifier = Modifier.height(10.dp))
            }
            Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                AttrImage(attr = skill.attributes, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(2.dp))
                Text(text = buildAnnotatedString {
                    append(skill.attributes.name!!)
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.surfaceTint)) {
                        append(skill.skillType.name)
                    }
                }, fontSize = 18.sp)
            }
            Text(text = buildAnnotatedString {
                append(skill.description)
                append("\n\n")
                append("附加效果: ")
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.onPrimaryContainer, fontSize = 16.sp)) {
                    append(skill.additional_effects)
                }
            }, fontSize = 18.sp, modifier = Modifier.padding(8.dp))
            Row(modifier = Modifier.padding(8.dp)) {
                Text(text = "威力: ${skill.value}", modifier = Modifier.weight(1f))
                Text(text = "PP: ${skill.amount}", modifier = Modifier.weight(1f))
            }
            Row(modifier = Modifier.padding(8.dp)) {
                Text(text = "是否必中: ${if (skill.isBe) "是" else "否"}", modifier = Modifier.weight(1f))
                Text(text = "能否遗传: ${if (skill.isGenetic) "是" else "否"}", modifier = Modifier.weight(1f))
            }
        }
        "receive -> $skill".logI()
    }
}

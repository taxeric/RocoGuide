package com.lanier.rocoguide.ui.page

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.lanier.plugin_base.logI
import com.lanier.rocoguide.R
import com.lanier.rocoguide.base.ROUTE_SCREEN_MAIN_PERSONALITY
import com.lanier.rocoguide.base.ROUTE_SCREEN_MAIN_SKILL_LIST
import com.lanier.rocoguide.ui.common.CommonBaseScaffold
import com.lanier.rocoguide.ui.common.SingleTitle
import com.lanier.rocoguide.ui.common.TitleText

/**
 * Create by Eric
 * on 2022/7/25
 */
@Composable
fun OtherScreen(navHostController: NavHostController, title: String){
    CommonBaseScaffold(title = title) {
        OthersMain(navHostController, padding = it)
    }
}

@Composable
fun OthersMain(navHostController: NavHostController, padding: PaddingValues){
    Column(modifier = Modifier.padding(padding)) {
        OtherCS(navHostController)
        Spacer(modifier = Modifier.height(10.dp))
        OtherDT(navHostController)
    }
}

@Composable
fun OtherCS(navHostController: NavHostController){
    Column(modifier = Modifier.fillMaxWidth()) {
        SingleTitle(title = "CS", 0.5f)
        OtherHorizontalTextItem("技能大全") {
            navHostController.navigate(ROUTE_SCREEN_MAIN_SKILL_LIST)
        }
        OtherHorizontalTextItem("性格修正") {
            navHostController.navigate(ROUTE_SCREEN_MAIN_PERSONALITY)
        }
    }
}

@Composable
fun OtherDT(navHostController: NavHostController){
    Column(modifier = Modifier.fillMaxWidth()) {
        SingleTitle(title = "DT", 0.5f)
        GlanceTips()
        VersionText()
        AboutText()
    }
}

@Composable
private fun OtherHorizontalTextItem(title: String, click: () -> Unit = {}){
    Text(text = title, modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.background)
        .clickable {
            click()
        }
        .padding(10.dp))
}

@Composable
fun GlanceTips(){
    TitleText(title = "小部件", text = stringResource(id = R.string.glance_tips),
        titleSize = 16.sp,
        titleWeight = null,
        textColor = MaterialTheme.colorScheme.outline,)
}

@Composable
fun AboutText(){
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    TitleText("关于",
        stringResource(id = R.string.about),
        titleSize = 16.sp,
        titleWeight = null,
        textColor = MaterialTheme.colorScheme.outline
    ) {
        clipboardManager.setText(buildAnnotatedString { append("LBA2460") })
        Toast.makeText(context, "Copied!", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun VersionText(click: () -> Unit = {}){
    val context = LocalContext.current
    val pi = context.packageManager.getPackageInfo(context.packageName, 0)
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.background)
        .clickable {
            click()
        }
        .padding(10.dp)
    ) {
        val (idKey, idValue) = createRefs()
        Text(text = "版本", modifier = Modifier.constrainAs(idKey) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        })
        Text(text = pi.versionName, fontSize = 14.sp,
            color = MaterialTheme.colorScheme.surfaceTint,
            modifier = Modifier.constrainAs(idValue) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
        })
    }
}

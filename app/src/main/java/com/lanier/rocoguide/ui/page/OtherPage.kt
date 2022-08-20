package com.lanier.rocoguide.ui.page

import android.content.ComponentName
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.lanier.plugin_base.PluginLoader
import com.lanier.rocoguide.R

/**
 * Create by Eric
 * on 2022/7/25
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtherScreen(navHostController: NavHostController, title: String){
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            SmallTopAppBar(
                title = { Text(text = title) },
            )
        }
    ){ innerPadding ->
        OthersMain(navHostController, padding = innerPadding)
    }
}

@Composable
fun OthersMain(navHostController: NavHostController, padding: PaddingValues){
    val context = LocalContext.current
    val gifImageLoader = ImageLoader.Builder(context)
        .components {
            if (android.os.Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Column(modifier = Modifier.padding(padding)) {
        Button(onClick = {
            PluginLoader.loadAssetsPluginApk(context, "test.apk"){ result, msg, resources ->
                if (result){
                    val intent = Intent().apply {
                        component = ComponentName(context, "com.lanier.gplugin.MainActivity")
                    }
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                }
            }
        }) {
            Text(text = "遗传")
        }
        val painter = rememberAsyncImagePainter(model = R.drawable.dimo, gifImageLoader)
        Image(painter = painter, contentDescription = "")
        val p1 = rememberAsyncImagePainter(model = R.drawable.ic_dimo_1)
        Image(painter = p1, contentDescription = "")
    }
}

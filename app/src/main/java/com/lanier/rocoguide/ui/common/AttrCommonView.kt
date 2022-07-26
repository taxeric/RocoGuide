package com.lanier.rocoguide.ui.common

import android.graphics.Rect
import android.text.TextPaint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.lanier.rocoguide.R
import com.lanier.rocoguide.entity.SpiritAttributes
import com.lanier.rocoguide.entity.SpiritEggGroup
import com.lanier.rocoguide.entity.SpiritEntity
import com.lanier.rocoguide.utils.logI
import kotlin.math.sqrt

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/8 16:20
 * Desc  :
 */
@Composable
fun AttrImage(modifier: Modifier = Modifier, attr: SpiritAttributes, contentScale: ContentScale = ContentScale.Crop) {
    Image(painter = painterResource(id = when (attr.id) {
        1 -> R.drawable.ic_attr_1
        2 -> R.drawable.ic_attr_2
        3 -> R.drawable.ic_attr_3
        4 -> R.drawable.ic_attr_4
        5 -> R.drawable.ic_attr_5
        6 -> R.drawable.ic_attr_6
        7 -> R.drawable.ic_attr_7
        8 -> R.drawable.ic_attr_8
        9 -> R.drawable.ic_attr_9
        10 -> R.drawable.ic_attr_10
        11 -> R.drawable.ic_attr_11
        12 -> R.drawable.ic_attr_12
        13 -> R.drawable.ic_attr_13
        14 -> R.drawable.ic_attr_14
        15 -> R.drawable.ic_attr_15
        16 -> R.drawable.ic_attr_16
        17 -> R.drawable.ic_attr_17
        18 -> R.drawable.ic_attr_18
        19 -> R.drawable.ic_attr_19
        20 -> R.drawable.ic_attr_20
        21 -> R.drawable.ic_attr_21
        else -> R.drawable.ic_attr_0
    }), contentDescription = "attr", contentScale = contentScale, modifier = modifier)
}

@Composable
fun EggImage(modifier: Modifier = Modifier, eg: SpiritEggGroup, contentScale: ContentScale = ContentScale.Crop) {
    Image(painter = painterResource(id = when(eg.id) {
        2 -> R.drawable.ic_egg_plant
        3 -> R.drawable.ic_egg_spirit
        4 -> R.drawable.ic_egg_sky
        5 -> R.drawable.ic_egg_immortal
        6 -> R.drawable.ic_egg_clever
        7 -> R.drawable.ic_egg_guard
        8 -> R.drawable.ic_egg_power
        9 -> R.drawable.ic_egg_ground
        10 -> R.drawable.ic_egg_animal
        else -> R.drawable.ic_egg_group_unknow
    }), contentDescription = "egg", contentScale = contentScale, modifier = modifier)
}

@Composable
fun EggImage(modifier: Modifier = Modifier, id: Int, contentScale: ContentScale = ContentScale.Crop) {
    Image(painter = painterResource(id = when(id) {
        2 -> R.drawable.ic_egg_plant
        3 -> R.drawable.ic_egg_spirit
        4 -> R.drawable.ic_egg_sky
        5 -> R.drawable.ic_egg_immortal
        6 -> R.drawable.ic_egg_clever
        7 -> R.drawable.ic_egg_guard
        8 -> R.drawable.ic_egg_power
        9 -> R.drawable.ic_egg_ground
        10 -> R.drawable.ic_egg_animal
        else -> R.drawable.ic_egg_group_unknow
    }), contentDescription = "egg", contentScale = contentScale, modifier = modifier)
}

@Composable
fun EggGroupImage(modifier: Modifier = Modifier, eg: SpiritEggGroup, contentScale: ContentScale = ContentScale.Crop) {
    Image(painter = painterResource(id = when(eg.id) {
        2 -> R.drawable.ic_egg_group_plant
        3 -> R.drawable.ic_egg_group_spirit
        4 -> R.drawable.ic_egg_group_sky
        5 -> R.drawable.ic_egg_group_immortal
        6 -> R.drawable.ic_egg_group_clever
        7 -> R.drawable.ic_egg_group_guard
        8 -> R.drawable.ic_egg_group_power
        9 -> R.drawable.ic_egg_group_ground
        10 -> R.drawable.ic_egg_group_animal
        else -> R.drawable.ic_egg_group_unknow
    }), contentDescription = "eggGroup", contentScale = contentScale, modifier = modifier)
}

@Composable
fun EggGroupImage(modifier: Modifier = Modifier, id: Int) {
    Image(painter = painterResource(id = when(id) {
        2 -> R.drawable.ic_egg_group_plant
        3 -> R.drawable.ic_egg_group_spirit
        4 -> R.drawable.ic_egg_group_sky
        5 -> R.drawable.ic_egg_group_immortal
        6 -> R.drawable.ic_egg_group_clever
        7 -> R.drawable.ic_egg_group_guard
        8 -> R.drawable.ic_egg_group_power
        9 -> R.drawable.ic_egg_group_ground
        10 -> R.drawable.ic_egg_group_animal
        else -> R.drawable.ic_egg_group_unknow
    }), contentDescription = "eggGroup", contentScale = ContentScale.Crop, modifier = modifier)
}

@Composable
fun RacialHexagonal(
    modifier: Modifier = Modifier,
    spiritData: SpiritEntity,
    powerTextColor: Color = Color.LightGray,
    attackTextColor: Color = Color.LightGray,
    defenseTextColor: Color = Color.LightGray,
    magicAttackTextColor: Color = Color.LightGray,
    magicDefenseTextColor: Color = Color.LightGray,
    speedTextColor: Color = Color.LightGray,
    baseHexagonalColor: Color = Color.Yellow,
    radiationColor: Color = Color.LightGray,
    realRacialValueLineColor: Color
){
    var size by remember {
        mutableStateOf(Size.Zero)
    }
    var pxWidthValue = 0f
    var pxHeightValue = 0f
    val maxRacial = with(LocalDensity.current) { 200.dp.toPx() }
    val pxRacialPower = with(LocalDensity.current) { spiritData.racePower.dp.toPx() }
    val pxRacialAttack = with(LocalDensity.current) { spiritData.raceAttack.dp.toPx() }
    val pxRacialDefense = with(LocalDensity.current) { spiritData.raceDefense.dp.toPx() }
    val pxRacialMagicAttack = with(LocalDensity.current) { spiritData.raceMagicAttack.dp.toPx() }
    val pxRacialMagicDefense = with(LocalDensity.current) { spiritData.raceMagicDefense.dp.toPx() }
    val pxRacialSpeed = with(LocalDensity.current) { spiritData.raceSpeed.dp.toPx() }
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                size = it.size.toSize()
                pxWidthValue = size.width
                pxHeightValue = size.height
            }
    ) {
        val hexagonalTopBottomPadding = 40f
        val baseCosValue = sqrt(3f) / 2
        val middleWidth = pxWidthValue / 2f
        val middleHeight = pxHeightValue / 2f
        val radius = middleHeight - hexagonalTopBottomPadding
/*        drawRect(color = Color.Red, size = Size(pxValue, pxValue), style = Stroke(1f))
        drawCircle(
            color = Color.Red,
            radius = middleHeight - hexagonalTopBottomPadding,
            center = Offset(middleWidth, middleWidth),
            style = Stroke(1f)
        )*/
        val centerPoint = Offset(middleWidth, middleHeight)
        val aPoint = Offset(middleWidth, 0f + hexagonalTopBottomPadding)
        val bPoint = Offset(
            middleWidth - baseCosValue * radius,
            middleHeight - radius / 2f
        )
        val cPoint = Offset(
            middleWidth - baseCosValue * radius,
            middleHeight + radius / 2f
        )
        val dPoint = Offset(middleWidth, pxHeightValue - hexagonalTopBottomPadding)
        val ePoint = Offset(
            middleWidth + baseCosValue * radius,
            middleHeight + radius / 2f
        )
        val fPoint = Offset(
            middleWidth + baseCosValue * radius,
            middleHeight - radius / 2f
        )
        //a->b
        drawLine(
            color = baseHexagonalColor,
            start = aPoint,
            end = bPoint,
            strokeWidth = 2f
        )
        //b->c
        drawLine(
            color = baseHexagonalColor,
            start = bPoint,
            end = cPoint,
            strokeWidth = 2f
        )
        //c->d
        drawLine(
            color = baseHexagonalColor,
            start = cPoint,
            end = dPoint,
            strokeWidth = 2f
        )
        //d->e
        drawLine(
            color = baseHexagonalColor,
            start = dPoint,
            end = ePoint,
            strokeWidth = 2f
        )
        //e->f
        drawLine(
            color = baseHexagonalColor,
            start = ePoint,
            end = fPoint,
            strokeWidth = 2f
        )
        //f->a
        drawLine(
            color = baseHexagonalColor,
            start = fPoint,
            end = aPoint,
            strokeWidth = 2f
        )
        //draw o->base point
        drawLine(
            color = radiationColor,
            start = centerPoint,
            end = aPoint,
        )
        drawLine(
            color = radiationColor,
            start = centerPoint,
            end = bPoint,
        )
        drawLine(
            color = radiationColor,
            start = centerPoint,
            end = cPoint,
        )
        drawLine(
            color = radiationColor,
            start = centerPoint,
            end = dPoint,
        )
        drawLine(
            color = radiationColor,
            start = centerPoint,
            end = ePoint,
        )
        drawLine(
            color = radiationColor,
            start = centerPoint,
            end = fPoint,
        )
        drawIntoCanvas { canvas ->
            val textPaint = TextPaint().apply {
                this.color = android.graphics.Color.WHITE
                this.textSize = 32f
            }
            val rect = Rect()
            val powerText = "精力 ${spiritData.racePower}"
            textPaint.color = android.graphics.Color.argb(
                powerTextColor.alpha, powerTextColor.red, powerTextColor.green, powerTextColor.blue
            )
            textPaint.getTextBounds(powerText, 0, powerText.length - 1, rect)
            canvas.nativeCanvas.drawText(
                powerText,
                aPoint.x - rect.width() / 2f,
                hexagonalTopBottomPadding - 12f,
                textPaint
            )
            val attackText = "攻击 ${spiritData.raceAttack}"
            textPaint.color = android.graphics.Color.argb(
                attackTextColor.alpha, attackTextColor.red, attackTextColor.green, attackTextColor.blue
            )
            textPaint.getTextBounds(attackText, 0, attackText.length - 1, rect)
            canvas.nativeCanvas.drawText(
                attackText,
                bPoint.x - rect.width() / 1.5f,
                bPoint.y - hexagonalTopBottomPadding - 12f,
                textPaint
            )
            val defenseText = "防御 ${spiritData.raceDefense}"
            textPaint.color = android.graphics.Color.argb(
                defenseTextColor.alpha, defenseTextColor.red, defenseTextColor.green, defenseTextColor.blue
            )
            textPaint.getTextBounds(defenseText, 0, defenseText.length - 1, rect)
            canvas.nativeCanvas.drawText(
                defenseText,
                cPoint.x - rect.width() / 1.5f,
                cPoint.y + hexagonalTopBottomPadding + 20f,
                textPaint
            )
            val speedText = "速度 ${spiritData.raceSpeed}"
            textPaint.color = android.graphics.Color.argb(
                speedTextColor.alpha, speedTextColor.red, speedTextColor.green, speedTextColor.blue
            )
            textPaint.getTextBounds(speedText, 0, speedText.length - 1, rect)
            canvas.nativeCanvas.drawText(
                speedText,
                dPoint.x - rect.width() / 2,
                dPoint.y + hexagonalTopBottomPadding - 2f,
                textPaint
            )
            val magicDefenseText = "魔抗 ${spiritData.raceMagicDefense}"
            textPaint.color = android.graphics.Color.argb(
                magicDefenseTextColor.alpha, magicDefenseTextColor.red, magicDefenseTextColor.green, magicDefenseTextColor.blue
            )
            textPaint.getTextBounds(magicDefenseText, 0, magicDefenseText.length - 1, rect)
            canvas.nativeCanvas.drawText(
                magicDefenseText,
                ePoint.x - rect.width() / 2,
                ePoint.y + hexagonalTopBottomPadding + 20f,
                textPaint
            )
            val magicAttackText = "魔攻 ${spiritData.raceMagicAttack}"
            textPaint.color = android.graphics.Color.argb(
                magicAttackTextColor.alpha, magicAttackTextColor.red, magicAttackTextColor.green, magicAttackTextColor.blue
            )
            textPaint.getTextBounds(magicAttackText, 0, magicAttackText.length - 1, rect)
            canvas.nativeCanvas.drawText(
                magicAttackText,
                fPoint.x - rect.width() / 2,
                fPoint.y - hexagonalTopBottomPadding - 12f,
                textPaint
            )
        }
        //draw racial values
        val powerPoint = Offset(
            middleWidth,
            if (pxRacialPower > 0f) {
                middleHeight - pxRacialPower / maxRacial * radius
            } else {
                middleHeight
            }
        )
        val realAttack = pxRacialAttack / maxRacial * radius
        val attackPoint = Offset(
            if (pxRacialAttack > 0f) {
                middleWidth - realAttack * baseCosValue
            } else {
                middleWidth
            },
            if (pxRacialAttack > 0f) {
                middleHeight - realAttack / 2f
            } else {
                middleHeight
            }
        )
        val realDefense = pxRacialDefense / maxRacial * radius
        val defensePoint = Offset(
            if (pxRacialDefense > 0f) {
                middleWidth - realDefense * baseCosValue
            } else {
                middleWidth
            },
            if (pxRacialDefense > 0f) {
                middleHeight + realDefense / 2f
            } else {
                middleHeight
            }
        )
        val speedPoint = Offset(
            middleWidth,
            if (pxRacialSpeed > 0f) {
                middleHeight + pxRacialSpeed / maxRacial * radius
            } else {
                middleHeight
            }
        )
        val realMagicDefense = pxRacialMagicDefense / maxRacial * radius
        val magicDefensePoint = Offset(
            if (pxRacialMagicDefense > 0f) {
                middleWidth + realMagicDefense * baseCosValue
            } else {
                middleWidth
            },
            if (pxRacialMagicDefense > 0f) {
                middleHeight + realMagicDefense / 2f
            } else {
                middleHeight
            }
        )
        val realMagicAttack = pxRacialMagicAttack / maxRacial * radius
        val magicAttackPoint = Offset(
            if (pxRacialMagicAttack > 0f) {
                middleWidth + realMagicAttack * baseCosValue
            } else {
                middleWidth
            },
            if (pxRacialMagicAttack > 0f) {
                middleHeight - realMagicAttack / 2f
            } else {
                middleHeight
            }
        )
        drawLine(
            color = realRacialValueLineColor,
            start = powerPoint,
            end = attackPoint
        )
        drawLine(
            color = realRacialValueLineColor,
            start = attackPoint,
            end = defensePoint
        )
        drawLine(
            color = realRacialValueLineColor,
            start = defensePoint,
            end = speedPoint
        )
        drawLine(
            color = realRacialValueLineColor,
            start = speedPoint,
            end = magicDefensePoint
        )
        drawLine(
            color = realRacialValueLineColor,
            start = magicDefensePoint,
            end = magicAttackPoint
        )
        drawLine(
            color = realRacialValueLineColor,
            start = magicAttackPoint,
            end = powerPoint
        )
    }
}

fun Color.toAString(): String{
    return "#$alpha$red$green$blue"
}

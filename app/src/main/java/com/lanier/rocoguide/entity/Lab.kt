package com.lanier.rocoguide.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.lanier.rocoguide.R

/**
 * Create by Eric
 * on 2023/1/25
 */
data class DynamicIconEntity(
    @StringRes val launchName: Int,
    @DrawableRes val resId: Int = R.mipmap.ic_roco_logo,
    val selected: Boolean = false,
)

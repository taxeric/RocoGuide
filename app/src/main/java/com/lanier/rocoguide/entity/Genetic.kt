package com.lanier.rocoguide.entity

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/29 14:58
 * Desc  :
 */
data class GeneticSpiritData(
    val father: SpiritEntity = SpiritEntity(male = true),
    val mother: SpiritEntity = SpiritEntity(male = false),
    val skills: List<Skill> = emptyList()
)

data class BaseSpiritEntity(
    val `data`: List<GeneticSpiritData> = emptyList(),
    val groupId: String = ""
)

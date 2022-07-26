package com.lanier.rocoguide.entity

/**
 * Create by Eric
 * on 2022/7/26
 */
data class SpiritList(
    val data: List<SpiritEntity> = emptyList(),
    val totalItem: Int = 0
)

data class SpiritEntity(
    val avatar: String = "",
    val description: String = "",
    val group_id: Int = 0,
    val height: Double = 0.0,
    val hobby: String = "",
    val name: String = "",
    val number: Int = 0,
    val primary_attributes_id: Int = 0,
    val race_attack: Int = 0,
    val race_defense: Int = 0,
    val race_magic_attack: Int = 0,
    val race_magic_defense: Int = 0,
    val race_power: Int = 0,
    val race_speed: Int = 0,
    val secondary_attributes_id: Int = 0,
    val skills: List<String> = emptyList(),
    val weight: Double = 0.0
)


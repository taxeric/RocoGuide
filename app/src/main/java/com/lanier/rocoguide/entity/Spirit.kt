package com.lanier.rocoguide.entity

/**
 * Create by Eric
 * on 2022/7/26
 */
data class SpiritList(
    val code: Int = 0,
    val msg: String = "",
    val data: List<SpiritEntity> = emptyList(),
    val totalItem: Int = 0
)

data class SpiritDetailEntity(
    val code: Int = 0,
    val msg: String = "",
    val data: SpiritEntity = SpiritEntity()
)

data class SpiritEntity(
    val avatar: String = "",
    val description: String = "",
    val group: SpiritEggGroup = SpiritEggGroup(),
    val height: Double = 0.0,
    val hobby: String = "",
    val name: String = "",
    val number: Int = 0,
    val primaryAttributes: SpiritAttributes = SpiritAttributes(),
    val raceAttack: Int = 0,
    val raceDefense: Int = 0,
    val raceMagicAttack: Int = 0,
    val raceMagicDefense: Int = 0,
    val racePower: Int = 0,
    val raceSpeed: Int = 0,
    val secondaryAttributes: SpiritAttributes = SpiritAttributes(),
    val skills: List<Skill> = emptyList(),
    val weight: Double = 0.0
)

data class Skill(
    val additional_effects: String = "",
    val amount: Int = 0,
    val attributes: SpiritAttributes = SpiritAttributes(),
    val description: String = "",
    val id: Int = 0,
    val isBe: Boolean = false,
    val isGenetic: Boolean = false,
    val name: String = "",
    val skillType: SkillType = SkillType(),
    val speed: Int = 0,
    val value: Int = 0
)

data class SpiritEggGroup(
    val id: Int = 0,
    val name: String = ""
)

data class SpiritAttributes(
    val id: Int? = 0,
    val name: String? = ""
)

data class SkillType(
    val id: Int = 0,
    val name: String = ""
)

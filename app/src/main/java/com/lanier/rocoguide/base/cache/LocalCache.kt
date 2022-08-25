package com.lanier.rocoguide.base.cache

import com.lanier.rocoguide.entity.PersonalityEntity

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/25 17:03
 * Desc  :
 */
object LocalCache {

    // <editor-fold defaultstate="collapsed" desc="性格">
    // <editor-fold defaultstate="collapsed" desc="平衡性格">
    val balancePersonalities = mutableListOf<PersonalityEntity>().apply {
            add(PersonalityEntity("实干", "-", "-", 0))
            add(PersonalityEntity("害羞", "-", "-", 0))
            add(PersonalityEntity("认真", "-", "-", 0))
            add(PersonalityEntity("浮躁", "-", "-", 0))
            add(PersonalityEntity("坦率", "-", "-", 0))
        }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="+攻击性格">
    val attackPersonalities = mutableListOf<PersonalityEntity>().apply {
            add(PersonalityEntity("固执", "+10% 攻击", "-10% 魔攻", 1))
            add(PersonalityEntity("孤僻", "+10% 攻击", "-10% 防御", 1))
            add(PersonalityEntity("调皮", "+10% 攻击", "-10% 魔抗", 1))
            add(PersonalityEntity("勇敢", "+10% 攻击", "-10% 速度", 1))
        }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="+防御性格">
    val defensePersonalities = mutableListOf<PersonalityEntity>().apply {
            add(PersonalityEntity("悠闲", "+10% 防御", "-10% 速度", 2))
            add(PersonalityEntity("淘气", "+10% 防御", "-10% 魔攻", 2))
            add(PersonalityEntity("大胆", "+10% 防御", "-10% 攻击", 2))
            add(PersonalityEntity("无虑", "+10% 防御", "-10% 魔抗", 2))
        }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="魔攻性格">
    val magicAttackPersonalities = mutableListOf<PersonalityEntity>().apply {
            add(PersonalityEntity("保守", "+10% 魔攻", "-10% 攻击", 3))
            add(PersonalityEntity("马虎", "+10% 魔攻", "-10% 魔抗", 3))
            add(PersonalityEntity("稳重", "+10% 魔攻", "-10% 防御", 3))
            add(PersonalityEntity("冷静", "+10% 魔攻", "-10% 速度", 3))
        }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="魔抗性格">
    val magicDefensePersonalities = mutableListOf<PersonalityEntity>().apply {
            add(PersonalityEntity("狂妄", "+10% 魔抗", "-10% 速度", 4))
            add(PersonalityEntity("慎重", "+10% 魔抗", "-10% 魔攻", 4))
            add(PersonalityEntity("沉着", "+10% 魔抗", "-10% 攻击", 4))
            add(PersonalityEntity("温顺", "+10% 魔抗", "-10% 防御", 4))
        }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="+速度性格">
    val speedPersonalities = mutableListOf<PersonalityEntity>().apply {
            add(PersonalityEntity("胆小", "+10% 速度", "-10% 攻击", 5))
            add(PersonalityEntity("开朗", "+10% 速度", "-10% 魔攻", 5))
            add(PersonalityEntity("天真", "+10% 速度", "-10% 魔抗", 5))
            add(PersonalityEntity("急躁", "+10% 速度", "-10% 防御", 5))
        }
    // </editor-fold>
    // </editor-fold>
}
package com.lanier.rocoguide.base.cache

import android.graphics.Color
import com.google.gson.Gson
import com.lanier.rocoguide.R
import com.lanier.rocoguide.entity.*
import com.lanier.rocoguide.utils.logI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File
import java.util.*
import kotlin.collections.LinkedHashMap

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

    // <editor-fold defaultstate="collapsed" desc="遗传">
    private val gson = Gson()
    private val geneticStringBuilder = StringBuilder()
    private val geneticGroupMap = LinkedHashMap<String, SpiritEggGroup>()
    private val geneticSpiritMap = LinkedHashMap<String, List<GeneticSpiritData>>()

    fun getGeneticDataById(id: String): List<GeneticSpiritData> {
        if (geneticSpiritMap.containsKey(id)){
            return geneticSpiritMap[id]!!
        }
        return emptyList()
    }

    suspend fun initSpiritDataById(file: File, groupId: String, complete: (Boolean) -> Unit = {}) {
        val document = Jsoup.parse(file, )
        withContext(Dispatchers.IO) {
            initData(document, groupId)
        }
        complete(true)
    }

    private fun initData(document: Document?, groupId: String) {
        document?.run {
            geneticStringBuilder.delete(0, geneticStringBuilder.length)
            geneticStringBuilder.append("{\"groupId\":\"${groupId}\",\"data\":[")
            val trs = this.select("table").select("tr")
            trs.forEachIndexed {index, tr ->
                val tds = tr.select("td")
                if (tds.size == 3){
                    if (tds[2].text().contains("遗传") && tds[2].text().contains("技能")){
                        return@forEachIndexed
                    }
                    geneticStringBuilder.append("{")
                    geneticStringBuilder.append("\"father\":{\"name\":\"").append(tds[0].text()).append("\",\"male\":true},")
                    geneticStringBuilder.append("\"mother\":{\"name\":\"").append(tds[1].text()).append("\",\"male\":false},")
                    geneticStringBuilder.append("\"skills\":[")
                    val skills = tds[2].text().split("、")
                    skills.forEachIndexed { index1, skill ->
                        geneticStringBuilder.append("{\"name\":\"").append(skill).append("\"}")
                        if (index1 != skills.size - 1){
                            geneticStringBuilder.append(",")
                        }
                    }
                    geneticStringBuilder.append("]}")
                }
                if (index != trs.size - 1){
                    geneticStringBuilder.append(",")
                }
            }
            geneticStringBuilder.append("]}")
            val base = gson.fromJson(geneticStringBuilder.toString(), BaseSpiritEntity::class.java)
            geneticSpiritMap[groupId] = base.data
        }
    }

    fun calculateSkills(fatherName: String, motherName: String, groupId: String): Result<CalculateEntity>{
        if (!geneticSpiritMap.containsKey(groupId)){
            "需要初始化 $groupId".logI()
            return Result.success(CalculateEntity("需要初始化组别信息"))
        }
        //找到A公B母的指定精灵
        val allSkillsData = getSpiritGeneticSkillFromParentName(fatherName, motherName, groupId)
        if (allSkillsData == null){
            "未发现可遗传技能或父母有误".logI()
            return Result.success(CalculateEntity("未发现可遗传技能或父母有误"))
        }
        "第二代精灵 -> ${allSkillsData.father.name}[公] + ${allSkillsData.mother.name}[母] = ${allSkillsData.skills}".logI()
        //找到除了A公B母的所有A公+C母精灵
        val allFatherSkillsData = getSpiritGeneticsDataFromFamilyNameExceptSelf(fatherName, motherName, groupId)
        //存放所有符合A公C母=[X]的所有精灵(即与A公B母技能只要有一个一样的精灵)
        val acSpiritResult = mutableListOf<GeneticSpiritData>()
        //遍历A公B母孵出蛋蛋的所有技能
        allSkillsData.skills.forEachIndexed AgBm@{ index, skill ->
            //遍历除了A公B母的所有A公+C母精灵
            allFatherSkillsData.forEach AgCm@{ spirit ->
                var occurent = 0
                //遍历除了A公B母的所有A公+C母精灵技能
                spirit.skills.forEach AgCmSkill@{ baseSkill ->
                    //如果不是空技能
                    if (baseSkill.name != "无") {
                        //如果A公B母当前遍历的技能名与A公C母精灵技能名一致,则不再遍历A公C母的其他技能
                        if (skill.name == baseSkill.name) {
                            occurent ++
                            "same skill of spirits -> ${spirit.father.name} + ${spirit.mother.name} = $skill".logI()
                            val mSpirit = spirit.copy(skills = listOf(Skill(name = baseSkill.name)))
                            acSpiritResult.add(mSpirit)
                            return@AgCmSkill
                        }
                    }
                }
            }
        }
        //存放所有可携带多代遗传技能的精灵
        var resultInfo = ""
        val resultSpiritData = mutableListOf<GeneticSpiritData>()
        if (acSpiritResult.isNotEmpty()){
            "共发现 ${acSpiritResult.size} 组精灵".logI()
            //遍历A公C母,找到B公C母和C公B母的精灵组
            acSpiritResult.forEach { baseParent->
                val bgcm = getSpiritGeneticSkillFromParentName(motherName, baseParent.mother.name, groupId)
                val cgbm = getSpiritGeneticSkillFromParentName(baseParent.mother.name, motherName, groupId)
                if (bgcm != null){
                    //彩翼虫 + 咔咔鸟 = 信号之光
                    val list = bgcm.skills.toMutableList()
                    list.add(baseParent.skills[0])
                    val resultSpirit = bgcm.copy(skills = list)
                    resultSpiritData.add(resultSpirit)
                }
                if (cgbm != null){
                    //咔咔鸟 + 彩翼虫 = 风之保护
                    val list = cgbm.skills.toMutableList()
                    list.add(baseParent.skills[0])
                    val resultSpirit = cgbm.copy(skills = list)
                    resultSpiritData.add(resultSpirit)
                }
            }
        } else {
            "未发现可多代遗传精灵".logI()
            resultInfo = "未发现支持三代遗传的二代精灵"
        }
        if (resultSpiritData.isNotEmpty()){
            resultSpiritData.forEach { spiritData ->
                "第三代精灵 -> ${spiritData.father.name}[公] + ${spiritData.mother.name}[母] = ${spiritData.skills}".logI()
            }
        } else {
            resultSpiritData.clear()
            "未发现支持三代遗传的精灵".logI()
        }
        return Result.success(CalculateEntity(resultInfo, resultSpiritData))
    }

    fun getSpiritGeneticsDataFromFamilyNameExceptSelf(fatherName: String, motherName: String, groupId: String): List<GeneticSpiritData>{
        val baseSpiritData = getSpiritDataByGroupId(groupId)
        val result = mutableListOf<GeneticSpiritData>()
        baseSpiritData.forEach {
            if (it.father.name == fatherName && it.mother.name != motherName){
                result.add(it)
            }
        }
        return result
    }

    fun getSpiritGeneticSkillFromParentName(fatherName: String, motherName: String, groupId: String): GeneticSpiritData?{
        val baseSpiritData = getSpiritDataByGroupId(groupId)
        val result = mutableListOf<GeneticSpiritData>()
        baseSpiritData.forEach {
            if (it.father.name == fatherName && it.mother.name == motherName){
                result.add(it)
                return@forEach
            }
        }
        if (result.isEmpty()){
            return null
        }
        return result[0]
    }

    fun getSpiritGeneticSkillFromFatherName(name: String, groupId: String): List<GeneticSpiritData>{
        val baseSpiritData = getSpiritDataByGroupId(groupId)
        val result = mutableListOf<GeneticSpiritData>()
        baseSpiritData.forEach {
            if (it.father.name == name){
                result.add(it)
            }
        }
        return result
    }

    fun getSpiritDataByGroupId(id: String): List<GeneticSpiritData>{
        if (geneticSpiritMap.containsKey(id)){
            return geneticSpiritMap[id]!!
        }
        return emptyList()
    }

    data class CalculateEntity(
        val errorMsg: String = "",
        val data: List<GeneticSpiritData> = emptyList()
    )
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="版本">
    var newestData = ChangeLogData(isNewestVersion = false)
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="组别">
    val localCacheEggGroupInfo = mutableListOf<SpiritEggGroup>()
    val defaultUnknownEggGroup = mutableListOf<SpiritEggGroup>().apply {
        add(SpiritEggGroup(-99, res = R.drawable.ic_egg_unknow_1))
        add(SpiritEggGroup(-99, res = R.drawable.ic_egg_unknow_2))
        add(SpiritEggGroup(-99, res = R.drawable.ic_egg_unknow_3))
        add(SpiritEggGroup(-99, res = R.drawable.ic_egg_unknow_4))
        add(SpiritEggGroup(-99, res = R.drawable.ic_egg_unknow_5))
        add(SpiritEggGroup(-99, res = R.drawable.ic_egg_unknow_6))
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="随机色">
    fun generateRandomColor(): Int {
        val rnd = Random()
        return Color.argb(127, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="感谢名单">
    data class ThankEntity(
        val name: String,
        val desc: String,
        val url: String
    )
    val thanksList = mutableListOf<ThankEntity>().apply {
        add(ThankEntity("Compose-WebView",
            "适用于Compose的浏览器控件",
            "https://google.github.io/accompanist/webview/"))
        add(ThankEntity("Compose-Refresh",
            "适用于Compose的下拉刷新框架",
            "https://google.github.io/accompanist/swiperefresh/"))
        add(ThankEntity("Compose-Paging3",
            "适用于Compose的数据分页库",
            "https://developer.android.google.cn/topic/libraries/architecture/paging/v3-overview"))
        add(ThankEntity("Compose-Navigation",
            "适用于Compose的路由框架",
            "https://developer.android.google.cn/guide/navigation/navigation-getting-started"))
        add(ThankEntity("Compose-Coil",
            "适用于Compose的图片加载控件",
            "https://github.com/coil-kt/coil/blob/main/README-zh.md"))
        add(ThankEntity("Compose-Glance",
            "适用于Compose的小部件开发框架",
            "https://developer.android.com/reference/kotlin/androidx/glance/package-summary"))
        add(ThankEntity("Jsoup",
            "适用于Java的html解析器",
            "https://jsoup.org/"))
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="场景BGM">
    val bgmList = mutableListOf<RemoteMusicEntity>()
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="当前下载内容类型">
    var currentDownloadContent = MutableStateFlow<CurrentDownloadContent>(
        CurrentDownloadContent.Other
    )
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="系列">
    val seriesList = mutableListOf<SeriesEntity>()
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="网络">
    const val BASE_URL = "your host"
    // </editor-fold>
}

sealed class CurrentDownloadContent{
    object APK: CurrentDownloadContent()
    object UpdateApk: CurrentDownloadContent()
    object Other: CurrentDownloadContent()
}
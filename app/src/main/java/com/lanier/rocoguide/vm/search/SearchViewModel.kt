package com.lanier.rocoguide.vm.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lanier.rocoguide.vm.skill.SkillRepo
import com.lanier.rocoguide.vm.spirit.SpiritRepo

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/8 14:38
 * Desc  :
 */
class SearchViewModel(
    keywords: String
) : ViewModel(){

    private val spiritRepo = SpiritRepo()
    private val skillRepo = SkillRepo()

    val searchSpiritResult = spiritRepo.searchSpirit(keywords).cachedIn(viewModelScope)
    val searchSkillResult = skillRepo.searchSkill(keywords).cachedIn(viewModelScope)
}

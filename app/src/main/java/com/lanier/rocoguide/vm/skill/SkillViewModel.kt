package com.lanier.rocoguide.vm.skill

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lanier.rocoguide.vm.skill.SkillRepo

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/25 10:10
 * Desc  :
 */
class SkillViewModel: ViewModel() {

    private val repo = SkillRepo()

    val skillMainListFlow = repo.getAllSkill().cachedIn(viewModelScope)
}
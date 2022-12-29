package com.lanier.rocoguide.vm.spirit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanier.rocoguide.entity.SpiritDetailEntity
import com.lanier.rocoguide.entity.SpiritEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/9 15:23
 * Desc  :
 */
class SpiritDetailViewModel: ViewModel() {

    private val repo = SpiritRepo()

    private val _detail = MutableStateFlow(SpiritEntity(id = -2))
    val spiritDetail get() = _detail

    fun getSpiritById(id: Int) {
        viewModelScope.launch {
            val response = repo.searchSpiritById(id)
            delay(1000)
            if (response.isSuccess) {
                val detail = response.getOrDefault(SpiritDetailEntity(code = -1))
                if (detail.code == 200) {
                    val spirit = detail.data
                    val mSpirit = if (spirit.id.toString() != spirit.number) {
                        spirit.copy(id = -1)
                    } else {
                        spirit
                    }
                    _detail.tryEmit(mSpirit)
                } else {
                    _detail.tryEmit(SpiritEntity(id = -3))
                }
            } else {
                _detail.tryEmit(SpiritEntity(id = -4))
            }
        }
    }
}
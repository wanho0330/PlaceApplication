package com.wanho.data.local

import android.content.Context
import com.wanho.data.entity.PlaceEntity
import com.wanho.domain.model.PlaceModel
import kotlinx.coroutines.CoroutineScope


interface PlaceLocalSource {

    // 다녀온 식당 내역을 저장함
    fun setHistory(placeEntity: PlaceEntity)

    // 다녀온 식당 내역을 SQLite 에서 가져옴
    fun getHistoryList() : List<PlaceEntity>

    // 다녀온 식당 내역을 모두 삭제함
    fun deleteAll()

    // 다녀온 식당 내역을 삭제함 : (PlaceEntity 고유 ID) -> Unit
    fun delete(id: String)
}


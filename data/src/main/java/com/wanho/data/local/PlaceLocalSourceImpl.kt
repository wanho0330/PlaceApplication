package com.wanho.data.local

import com.wanho.data.entity.PlaceEntity


class PlaceLocalSourceImpl(private val historyDao: HistoryDao) : PlaceLocalSource {

    override fun setHistory(placeEntity: PlaceEntity) {
        historyDao.insert(placeEntity)
    }

    override fun getHistoryList() : List<PlaceEntity> {
        return historyDao.getAll()
    }

    override fun deleteAll() {
        historyDao.deleteAll()
    }

    override fun delete(id: String) {
        historyDao.delete(id)
    }


}


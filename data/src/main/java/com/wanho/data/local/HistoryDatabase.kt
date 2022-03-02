package com.wanho.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wanho.data.entity.PlaceEntity


@Database(entities = [PlaceEntity::class], version = 1)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao() : HistoryDao

    // 실제 구현부는 Koin을 통해 생성

}


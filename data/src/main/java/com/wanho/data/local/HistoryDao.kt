package com.wanho.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.wanho.data.entity.PlaceEntity
import com.wanho.domain.model.PlaceModel



@Dao
interface HistoryDao {

    @Query("SELECT * FROM TB_PLACE")
    fun getAll() : List<PlaceEntity>

    // onConflict 속성을 통해 Update = Insert
    @Insert(onConflict = REPLACE)
    fun insert(placeEntity: PlaceEntity)

    @Query("DELETE FROM TB_PLACE")
    fun deleteAll()

    @Query("DELETE FROM TB_PLACE WHERE id=:id")
    fun delete(id : String)
}


package com.wanho.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wanho.domain.model.PlaceModel



@Entity(tableName = "TB_PLACE")
data class PlaceEntity(
    @PrimaryKey
    override val id : String,
    override val name: String,
    override val category: String,
    override val phone: String,
    override val distance: Int,
    override val placeUrl: String,
    override val roadAddressName: String,
    override val date : String?,
    override val latitude: Double,
    override val longitude: Double
) : PlaceModel
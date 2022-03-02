package com.wanho.domain.usecase

import com.wanho.domain.model.PlaceModel
import com.wanho.domain.repository.PlaceRepository

class SetHistoryUseCase(private val placeRepository: PlaceRepository) {

    fun invoke(id : String, name: String, category: String, phone: String, distance : Int, placeUrl : String, roadAddressName : String, date: String? , latitude : Double, longitude : Double) {
        placeRepository.setHistoryLocalSource(id, name, category, phone, distance, placeUrl, roadAddressName, date, latitude, longitude)
    }
}
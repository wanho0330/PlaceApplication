package com.wanho.domain.usecase

import com.wanho.domain.model.PlaceModel
import com.wanho.domain.repository.PlaceRepository



class GetHistoryUseCase(private val placeRepository: PlaceRepository) {

    fun invoke() : List<PlaceModel> = placeRepository.getHistoryLocalSource()


}

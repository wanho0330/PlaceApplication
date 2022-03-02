package com.wanho.domain.usecase

import com.wanho.domain.repository.PlaceRepository

class DeleteHistoryUseCase (private val placeRepository: PlaceRepository) {

    fun invoke(id : String) {
        placeRepository.deleteHistoryLocalSource(id)
    }
}
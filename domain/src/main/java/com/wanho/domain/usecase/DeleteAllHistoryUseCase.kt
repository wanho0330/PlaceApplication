package com.wanho.domain.usecase

import com.wanho.domain.repository.PlaceRepository

class DeleteAllHistoryUseCase (private val placeRepository: PlaceRepository) {

    fun invoke() {
        placeRepository.deleteAllLocalSource()
    }
}
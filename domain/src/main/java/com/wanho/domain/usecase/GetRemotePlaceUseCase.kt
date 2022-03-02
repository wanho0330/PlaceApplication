package com.wanho.domain.usecase

import com.wanho.domain.model.PlaceModel
import com.wanho.domain.repository.PlaceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class GetRemotePlaceUseCase(private val placeRepository: PlaceRepository) {

    fun invoke(latitude : String, longitude : String, radius : Int , scope : CoroutineScope, onResult : (List<PlaceModel>) -> Unit){
        scope.launch(Dispatchers.Main) {

            val deferred = async(Dispatchers.IO) {
                // async : 결과값을 Deferred 로 감싸서 반환함. 이 때 Deferred 는 미래에 올 수 있는 값을 담아 놓는 객체를 뜻함
                placeRepository.getPlaceRemoteSource(latitude, longitude, radius)
            }

            onResult(deferred.await())
            // await : 코루틴은 결과가 반환되기 까지 일시중지하고 기다림. -> 원격에서 데이터가 와야지 표시를 해줄 수 있으므로 결과를 기다려야함
        }

    }
}
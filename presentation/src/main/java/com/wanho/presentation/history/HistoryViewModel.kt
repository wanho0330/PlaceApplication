package com.wanho.presentation.history

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wanho.domain.model.PlaceModel
import com.wanho.domain.usecase.GetHistoryUseCase
import com.wanho.domain.usecase.DeleteAllHistoryUseCase
import com.wanho.domain.usecase.DeleteHistoryUseCase
import com.wanho.domain.usecase.SetHistoryUseCase
import com.wanho.presentation.utils.SingleLiveEvent
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// 다녀온 식당 내역의 뷰모델
class HistoryViewModel(private val getHistoryUseCase: GetHistoryUseCase, private val setHistoryUseCase: SetHistoryUseCase, private val deleteAllHistoryUseCase: DeleteAllHistoryUseCase, private val deleteHistoryUseCase: DeleteHistoryUseCase) : ViewModel() {

    private var _historyList: MutableLiveData<List<PlaceModel>> = MutableLiveData()
    val historyList: LiveData<List<PlaceModel>>
        get() = _historyList

    val singleLiveEvent: SingleLiveEvent<PlaceModel> = SingleLiveEvent()

    fun getHistoryAll() {
        _historyList.value = getHistoryUseCase.invoke()
    }

    // history Insert 함수
    // Domain Layer를 알지 못하므로 직접 값을 입력받아 저장함
    @RequiresApi(Build.VERSION_CODES.O)
    fun setHistory(id : String, name: String, category: String, phone: String, distance : Int, placeUrl : String, roadAddressName : String , latitude : Double, longitude : Double) {
        
        val current = LocalDate.now()
        val formatter = DateTimeFormatter.ISO_DATE
        val formatted = current.format(formatter)

        setHistoryUseCase.invoke(id, name, category, phone, distance, placeUrl, roadAddressName, formatted, latitude, longitude)
    }

    fun deleteAll() {
        deleteAllHistoryUseCase.invoke()
    }

    fun onItemClick(placeModel: PlaceModel) {
        singleLiveEvent.value = placeModel
    }

    fun onLongItemClick(placeModel: PlaceModel) {
        deleteHistoryUseCase.invoke(placeModel.id)
        getHistoryAll()
    }


}
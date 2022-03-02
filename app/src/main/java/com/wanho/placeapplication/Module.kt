package com.wanho.placeapplication

import androidx.room.Room
import com.wanho.data.local.HistoryDatabase
import com.wanho.data.local.PlaceLocalSource
import com.wanho.data.local.PlaceLocalSourceImpl
import com.wanho.data.remote.PlaceRemoteSource
import com.wanho.data.remote.PlaceRemoteSourceImpl
import com.wanho.data.repository.PlaceRepositoryImpl
import com.wanho.domain.repository.PlaceRepository
import com.wanho.domain.usecase.*
import com.wanho.presentation.history.HistoryViewModel
import com.wanho.presentation.search.LocationViewModel
import com.wanho.presentation.result.PlaceViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val BASE_URL = "https://dapi.kakao.com"


val viewModelModule = module {
    viewModel { PlaceViewModel( get() ) }
    viewModel { LocationViewModel() }
    viewModel { HistoryViewModel(get(), get(), get(), get()) }
}

val repositoryModule = module {
    single { PlaceRepositoryImpl(get(), get()) as PlaceRepository }
}

val useCaseModule = module {
    factory { GetRemotePlaceUseCase(get()) }
    factory { GetHistoryUseCase(get()) }
    factory { SetHistoryUseCase(get() ) }
    factory { DeleteAllHistoryUseCase(get()) }
    factory { DeleteHistoryUseCase(get()) }
}

val networkModule = module {
    single { PlaceRemoteSourceImpl() as PlaceRemoteSource }
}

val databaseModule = module {
    single { PlaceLocalSourceImpl(get()) as PlaceLocalSource }
    single { Room.databaseBuilder(get(), HistoryDatabase::class.java, "DB_HISTORY")
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()
    }
    single { get<HistoryDatabase>().historyDao() }
}


